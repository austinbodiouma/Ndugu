package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object BudgetsTable : Table("budgets") {
    val id = varchar("id", 36)
    val studentId = varchar("student_id", 36).references(StudentsTable.id)
    val category = varchar("category", 50)
    val limitAmount = decimal("limit_amount", 12, 2)
    val spentAmount = decimal("spent_amount", 12, 2).default(java.math.BigDecimal.ZERO)
    val month = integer("month")
    val year = integer("year")
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
