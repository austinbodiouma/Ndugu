package com.example.ndugu.feature.messaging.data.repository

import com.example.ndugu.core.data.auth.TokenStorage
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.core.domain.util.map
import com.example.ndugu.feature.messaging.data.mapper.toDomain
import com.example.ndugu.feature.messaging.data.remote.MessageRemoteDataSource
import com.example.ndugu.feature.messaging.data.remote.dto.SendMessageRequestDto
import com.example.ndugu.feature.messaging.domain.model.Conversation
import com.example.ndugu.feature.messaging.domain.model.Message
import com.example.ndugu.feature.messaging.domain.repository.MessageRepository

class MessageRepositoryImpl(
    private val remoteDataSource: MessageRemoteDataSource,
    private val tokenStorage: TokenStorage
) : MessageRepository {

    override suspend fun getConversations(): Result<List<Conversation>, DataError.Network> {
        return remoteDataSource.getConversations().map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override suspend fun getMessages(conversationId: String): Result<List<Message>, DataError.Network> {
        val currentUserId = tokenStorage.userId
        return remoteDataSource.getMessages(conversationId).map { dtos ->
            dtos.map { it.toDomain(currentUserId) }
        }
    }

    override suspend fun sendMessage(
        recipientId: String,
        content: String,
        conversationId: String?
    ): Result<Message, DataError.Network> {
        val currentUserId = tokenStorage.userId
        return remoteDataSource.sendMessage(
            SendMessageRequestDto(recipientId, content, conversationId)
        ).map { it.toDomain(currentUserId) }
    }
}
