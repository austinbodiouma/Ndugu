package com.example.ndugu.feature.messaging.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ConversationDto(
    val id: String,
    val otherParticipantName: String,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val unreadCount: Int
)

@Serializable
data class MessageDto(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val timestamp: String,
    val isRead: Boolean
)

@Serializable
data class SendMessageRequestDto(
    val recipientId: String,
    val content: String,
    val conversationId: String? = null
)
