package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ReversalRequestsTable : Table("reversal_requests") {
    val id = varchar("id", 36)
    val transactionId = varchar("transaction_id", 36).references(TransactionsTable.id)
    val requesterId = varchar("requester_id", 36).references(StudentsTable.id)
    val recipientId = varchar("recipient_id", 36).references(StudentsTable.id)
    val status = varchar("status", 20).default("PENDING") // PENDING, APPROVED, REJECTED
    val reason = varchar("reason", 500).nullable()
    val requestedAt = timestamp("requested_at")
    val resolvedAt = timestamp("resolved_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
