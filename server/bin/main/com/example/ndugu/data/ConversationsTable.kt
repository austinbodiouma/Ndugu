package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ConversationsTable : Table("conversations") {
    val id = varchar("id", 36)
    val title = varchar("title", 100).nullable()
    val listingId = varchar("listing_id", 36).references(ListingsTable.id).nullable()
    val orderId = varchar("order_id", 36).references(OrdersTable.id).nullable()
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
