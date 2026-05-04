package com.example.ndugu.service

import com.example.ndugu.data.*
import com.example.ndugu.data.DatabaseFactory.dbQuery
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import java.util.*

class MessageService {

    suspend fun getConversations(studentId: String): List<Map<String, Any>> = dbQuery {
        val conversationIds = ConversationParticipantsTable
            .selectAll()
            .where { ConversationParticipantsTable.studentId eq studentId }
            .map { it[ConversationParticipantsTable.conversationId] }

        ConversationsTable
            .selectAll()
            .where { ConversationsTable.id inList conversationIds }
            .orderBy(ConversationsTable.createdAt, SortOrder.DESC)
            .map { row ->
                val convId = row[ConversationsTable.id]
                val lastMessage = MessagesTable
                    .selectAll()
                    .where { MessagesTable.conversationId eq convId }
                    .orderBy(MessagesTable.createdAt, SortOrder.DESC)
                    .limit(1)
                    .singleOrNull()

                mapOf(
                    "id" to convId,
                    "title" to (row[ConversationsTable.title] ?: "Chat"),
                    "lastMessage" to (lastMessage?.get(MessagesTable.content) ?: ""),
                    "lastMessageAt" to (lastMessage?.get(MessagesTable.createdAt)?.toString() ?: row[ConversationsTable.createdAt].toString())
                )
            }
    }

    suspend fun getMessages(conversationId: String, limit: Int = 50): List<Map<String, Any>> = dbQuery {
        MessagesTable
            .selectAll()
            .where { MessagesTable.conversationId eq conversationId }
            .orderBy(MessagesTable.createdAt, SortOrder.ASC)
            .limit(limit)
            .map { row ->
                mapOf(
                    "id" to row[MessagesTable.id],
                    "senderId" to row[MessagesTable.senderId],
                    "content" to row[MessagesTable.content],
                    "createdAt" to row[MessagesTable.createdAt].toString()
                )
            }
    }

    suspend fun sendMessage(senderId: String, conversationId: String, content: String): String = dbQuery {
        val id = UUID.randomUUID().toString()
        val now = Clock.System.now()
        
        MessagesTable.insert {
            it[this.id] = id
            it[this.conversationId] = conversationId
            it[this.senderId] = senderId
            it[this.content] = content
            it[this.createdAt] = now
        }
        id
    }

    suspend fun startConversation(studentIds: List<String>, title: String? = null): String = dbQuery {
        val conversationId = UUID.randomUUID().toString()
        val now = Clock.System.now()

        ConversationsTable.insert {
            it[id] = conversationId
            it[this.title] = title
            it[this.createdAt] = now
        }

        studentIds.forEach { studentId ->
            ConversationParticipantsTable.insert {
                it[this.conversationId] = conversationId
                it[this.studentId] = studentId
            }
        }
        conversationId
    }
}
