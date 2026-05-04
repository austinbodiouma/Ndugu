package com.example.ndugu.feature.messaging.domain.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.messaging.domain.model.Conversation
import com.example.ndugu.feature.messaging.domain.model.Message

interface MessageRepository {
    suspend fun getConversations(): Result<List<Conversation>, DataError.Network>
    suspend fun getMessages(conversationId: String): Result<List<Message>, DataError.Network>
    suspend fun sendMessage(
        recipientId: String,
        content: String,
        conversationId: String? = null
    ): Result<Message, DataError.Network>
}
