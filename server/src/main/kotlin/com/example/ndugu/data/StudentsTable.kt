package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object StudentsTable : Table("students") {
    val id = varchar("id", 36)
    val phone = varchar("phone", 15).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex()
    val fullName = varchar("full_name", 100)
    val passwordHash = varchar("password_hash", 100)
    val isVerified = bool("is_verified").default(false)
    val otp = varchar("otp", 6).nullable()
    val otpExpiresAt = timestamp("otp_expires_at").nullable()
    val studentIdUrl = varchar("student_id_url", 500).nullable()
    val verificationStatus = varchar("verification_status", 20).default("PENDING")
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}
