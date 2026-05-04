package com.example.ndugu.service

import com.example.ndugu.data.DatabaseFactory.dbQuery
import com.example.ndugu.data.TransactionsTable
import com.example.ndugu.data.WalletsTable
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.util.*

class WalletService {

    suspend fun getWallet(studentId: String): Map<String, Any>? = dbQuery {
        val walletRow = WalletsTable.selectAll().where { WalletsTable.studentId eq studentId }.singleOrNull() ?: return@dbQuery null
        
        mapOf(
            "id" to walletRow[WalletsTable.id],
            "balance" to walletRow[WalletsTable.balance].toDouble(),
            "currency" to walletRow[WalletsTable.currency]
        )
    }

    suspend fun getTransactions(studentId: String, limit: Int = 20, offset: Long = 0): List<Map<String, Any>> = dbQuery {
        val walletRow = WalletsTable.selectAll().where { WalletsTable.studentId eq studentId }.singleOrNull() ?: return@dbQuery emptyList()
        val walletId = walletRow[WalletsTable.id]

        TransactionsTable.selectAll()
            .where { TransactionsTable.walletId eq walletId }
            .orderBy(TransactionsTable.createdAt, SortOrder.DESC)
            .limit(limit, offset = offset)
            .map { row ->
                mapOf(
                    "id" to row[TransactionsTable.id],
                    "type" to row[TransactionsTable.type],
                    "amount" to row[TransactionsTable.amount].toDouble(),
                    "counterpartyName" to (row[TransactionsTable.counterpartyName] ?: ""),
                    "memo" to (row[TransactionsTable.memo] ?: ""),
                    "category" to (row[TransactionsTable.category] ?: ""),
                    "status" to row[TransactionsTable.status],
                    "createdAt" to row[TransactionsTable.createdAt].toString()
                )
            }
    }
}
