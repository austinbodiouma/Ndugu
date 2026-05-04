package com.example.ndugu.service

import com.example.ndugu.data.*
import com.example.ndugu.data.DatabaseFactory.dbQuery
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.util.*

class PaymentService {

    suspend fun processQRPayment(studentId: String, qrCode: String, amount: BigDecimal): Boolean = dbQuery {
        // 1. Find Business
        val business = PartnerBusinessesTable.selectAll().where { PartnerBusinessesTable.qrCode eq qrCode }.singleOrNull() ?: return@dbQuery false
        val businessId = business[PartnerBusinessesTable.id]

        // 2. Find Student Wallet
        val wallet = WalletsTable.selectAll().where { WalletsTable.studentId eq studentId }.singleOrNull() ?: return@dbQuery false
        if (wallet[WalletsTable.balance] < amount) return@dbQuery false

        val now = Clock.System.now()

        // 3. Debit Student
        WalletsTable.update({ WalletsTable.id eq wallet[WalletsTable.id] }) {
            it[balance] = wallet[WalletsTable.balance] - amount
            it[updatedAt] = now
        }

        // 4. Record QR Payment
        val paymentId = UUID.randomUUID().toString()
        QRPaymentsTable.insert {
            it[id] = paymentId
            it[this.studentId] = studentId
            it[this.businessId] = businessId
            it[this.amount] = amount
            it[this.status] = "COMPLETED"
            it[this.createdAt] = now
        }

        // 5. Record Transaction
        TransactionsTable.insert {
            it[id] = UUID.randomUUID().toString()
            it[walletId] = wallet[WalletsTable.id]
            it[type] = "DEBIT"
            it[this.amount] = amount
            it[counterpartyName] = business[PartnerBusinessesTable.name]
            it[memo] = "QR Payment to ${business[PartnerBusinessesTable.name]}"
            it[category] = "PAYMENT"
            it[status] = "COMPLETED"
            it[referenceType] = "QR_PAYMENT"
            it[referenceId] = paymentId
            it[createdAt] = now
        }

        true
    }
}
