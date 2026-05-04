package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object OrdersTable : Table("orders") {
    val id = varchar("id", 36)
    val listingId = varchar("listing_id", 36).references(ListingsTable.id)
    val buyerId = varchar("buyer_id", 36).references(StudentsTable.id)
    val sellerId = varchar("seller_id", 36).references(StudentsTable.id)
    val quantity = integer("quantity").default(1)
    val totalAmount = decimal("total_amount", 12, 2)
    val status = varchar("status", 20).default("PENDING") // PENDING, PAID, DELIVERED, COMPLETED, DISPUTED, CANCELLED
    val escrowAmount = decimal("escrow_amount", 12, 2).default(java.math.BigDecimal.ZERO)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}
