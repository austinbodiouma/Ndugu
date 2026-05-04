package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ListingsTable : Table("listings") {
    val id = varchar("id", 36)
    val sellerId = varchar("seller_id", 36).references(StudentsTable.id)
    val title = varchar("title", 200)
    val description = text("description")
    val price = decimal("price", 12, 2)
    val category = varchar("category", 50)
    val stockQuantity = integer("stock_quantity").default(1)
    val status = varchar("status", 20).default("ACTIVE") // ACTIVE, SOLD, ARCHIVED
    val imageUrls = text("image_urls") // Stored as comma-separated or JSON
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}
