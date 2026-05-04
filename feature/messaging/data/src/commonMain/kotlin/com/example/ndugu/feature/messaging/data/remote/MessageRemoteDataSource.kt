package com.example.ndugu.feature.messaging.data.remote

import com.example.ndugu.core.data.networking.safeGet
import com.example.ndugu.core.data.networking.safePost
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.messaging.data.remote.dto.ConversationDto
import com.example.ndugu.feature.messaging.data.remote.dto.MessageDto
import com.example.ndugu.feature.messaging.data.remote.dto.SendMessageRequestDto
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

class MessageRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getConversations(): Result<List<ConversationDto>, DataError.Network> {
        return httpClient.safeGet("/api/messages/conversations")
    }

    suspend fun getMessages(conversationId: String): Result<List<MessageDto>, DataError.Network> {
        return httpClient.safeGet("/api/messages/conversations/$conversationId")
    }

    suspend fun sendMessage(request: SendMessageRequestDto): Result<MessageDto, DataError.Network> {
        return httpClient.safePost("/api/messages/send") {
            setBody(request)
        }
    }
}
