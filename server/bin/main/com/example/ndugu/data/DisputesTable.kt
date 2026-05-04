package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object DisputesTable : Table("disputes") {
    val id = varchar("id", 36)
    val orderId = varchar("order_id", 36).references(OrdersTable.id)
    val raisedById = varchar("raised_by_id", 36).references(StudentsTable.id)
    val reason = text("reason")
    val status = varchar("status", 20).default("OPEN") // OPEN, RESOLVED, CLOSED
    val resolution = text("resolution").nullable()
    val createdAt = timestamp("created_at")
    val resolvedAt = timestamp("resolved_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
