package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object PartnerBusinessesTable : Table("partner_businesses") {
    val id = varchar("id", 36)
    val name = varchar("name", 100)
    val qrCode = varchar("qr_code", 100).uniqueIndex()
    val contactPhone = varchar("contact_phone", 15)
    val category = varchar("category", 50)
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
