package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object TransactionsTable : Table("transactions") {
    val id = varchar("id", 36)
    val walletId = varchar("wallet_id", 36).references(WalletsTable.id)
    val type = varchar("type", 10) // CREDIT, DEBIT
    val amount = decimal("amount", 12, 2)
    val counterpartyId = varchar("counterparty_id", 36).nullable()
    val counterpartyName = varchar("counterparty_name", 100).nullable()
    val memo = varchar("memo", 500).nullable()
    val category = varchar("category", 50).nullable()
    val status = varchar("status", 20).default("COMPLETED")
    val referenceType = varchar("reference_type", 20).nullable() // TRANSFER, QR_PAYMENT, ORDER
    val referenceId = varchar("reference_id", 36).nullable()
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
