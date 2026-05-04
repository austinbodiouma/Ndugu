package com.example.ndugu.data

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object ConversationParticipantsTable : Table("conversation_participants") {
    val conversationId = varchar("conversation_id", 36).references(ConversationsTable.id)
    val studentId = varchar("student_id", 36).references(StudentsTable.id)
    val lastReadAt = timestamp("last_read_at").nullable()

    override val primaryKey = PrimaryKey(conversationId, studentId)
}
