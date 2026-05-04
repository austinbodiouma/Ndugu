package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object RatingsTable : Table("ratings") {
    val id = varchar("id", 36)
    val orderId = varchar("order_id", 36).references(OrdersTable.id)
    val raterId = varchar("rater_id", 36).references(StudentsTable.id)
    val ratedId = varchar("rated_id", 36).references(StudentsTable.id)
    val score = integer("score") // 1-5
    val review = text("review").nullable()
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
