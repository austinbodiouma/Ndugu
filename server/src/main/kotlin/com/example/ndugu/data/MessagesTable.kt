package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object MessagesTable : Table("messages") {
    val id = varchar("id", 36)
    val conversationId = varchar("conversation_id", 36).references(ConversationsTable.id)
    val senderId = varchar("sender_id", 36).references(StudentsTable.id)
    val content = text("content")
    val createdAt = timestamp("created_at")

    override val primaryKey = PrimaryKey(id)
}
