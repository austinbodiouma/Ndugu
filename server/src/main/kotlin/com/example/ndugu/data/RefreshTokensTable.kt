package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object RefreshTokensTable : Table("refresh_tokens") {
    val id = varchar("id", 36)
    val studentId = varchar("student_id", 36).references(StudentsTable.id)
    val tokenHash = varchar("token_hash", 256)
    val expiresAt = timestamp("expires_at")
    val createdAt = timestamp("created_at")
    val isRevoked = bool("is_revoked").default(false)

    override val primaryKey = PrimaryKey(id)
}
