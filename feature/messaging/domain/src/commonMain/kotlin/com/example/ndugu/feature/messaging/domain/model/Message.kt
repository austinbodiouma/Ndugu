package com.example.ndugu.feature.messaging.domain.model

data class Conversation(
    val id: String,
    val otherParticipantName: String,
    val lastMessage: String?,
    val lastMessageTime: String?,
    val unreadCount: Int
)

data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val timestamp: String,
    val isRead: Boolean,
    val isFromMe: Boolean
)
