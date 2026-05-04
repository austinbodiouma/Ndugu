package com.example.ndugu.service

import com.example.ndugu.data.*
import com.example.ndugu.data.DatabaseFactory.dbQuery
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.util.*

class TransferService {

    suspend fun sendMoney(senderId: String, recipientPhone: String, amount: BigDecimal, memo: String?): Boolean = dbQuery {
        // 1. Find Sender Wallet
        val senderWallet = WalletsTable.selectAll().where { WalletsTable.studentId eq senderId }.singleOrNull() ?: return@dbQuery false
        val senderBalance = senderWallet[WalletsTable.balance]
        if (senderBalance < amount) return@dbQuery false

        // 2. Find Recipient
        val recipientRow = StudentsTable.selectAll().where { StudentsTable.phone eq recipientPhone }.singleOrNull() ?: return@dbQuery false
        val recipientId = recipientRow[StudentsTable.id]
        val recipientWallet = WalletsTable.selectAll().where { WalletsTable.studentId eq recipientId }.singleOrNull() ?: return@dbQuery false

        val now = Clock.System.now()

        // 3. Debit Sender
        WalletsTable.update({ WalletsTable.id eq senderWallet[WalletsTable.id] }) {
            it[balance] = senderBalance - amount
            it[updatedAt] = now
        }

        // 4. Credit Recipient
        WalletsTable.update({ WalletsTable.id eq recipientWallet[WalletsTable.id] }) {
            it[balance] = recipientWallet[WalletsTable.balance] + amount
            it[updatedAt] = now
        }

        // 5. Create Transaction Records
        TransactionsTable.insert {
            it[id] = UUID.randomUUID().toString()
            it[walletId] = senderWallet[WalletsTable.id]
            it[type] = "DEBIT"
            it[this.amount] = amount
            it[counterpartyId] = recipientId
            it[counterpartyName] = recipientRow[StudentsTable.fullName]
            it[this.memo] = memo
            it[category] = "TRANSFER"
            it[status] = "COMPLETED"
            it[referenceType] = "TRANSFER"
            it[createdAt] = now
        }

        TransactionsTable.insert {
            it[id] = UUID.randomUUID().toString()
            it[walletId] = recipientWallet[WalletsTable.id]
            it[type] = "CREDIT"
            it[this.amount] = amount
            it[counterpartyId] = senderId
            it[counterpartyName] = (StudentsTable.selectAll().where { StudentsTable.id eq senderId }.single()[StudentsTable.fullName])
            it[this.memo] = memo
            it[category] = "TRANSFER"
            it[status] = "COMPLETED"
            it[referenceType] = "TRANSFER"
            it[createdAt] = now
        }

        true
    }
}
