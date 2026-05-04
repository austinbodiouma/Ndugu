package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object QRPaymentsTable : Table("qr_payments") {
    val id = varchar("id", 36)
    val studentId = varchar("student_id", 36).references(StudentsTable.id)
    val businessId = varchar("business_id", 36).references(PartnerBusinessesTable.id)
    val amount = decimal("amount", 12, 2)
    val status = varchar("status", 20).default("COMPLETED")
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
