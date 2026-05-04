package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object WalletsTable : Table("wallets") {
    val id = varchar("id", 36)
    val studentId = varchar("student_id", 36).references(StudentsTable.id).uniqueIndex()
    val balance = decimal("balance", 12, 2).default(java.math.BigDecimal.ZERO)
    val currency = varchar("currency", 3).default("KES")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}
