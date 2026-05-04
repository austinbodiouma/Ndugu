package com.example.ndugu.feature.messaging.data.mapper

import com.example.ndugu.feature.messaging.data.remote.dto.ConversationDto
import com.example.ndugu.feature.messaging.data.remote.dto.MessageDto
import com.example.ndugu.feature.messaging.domain.model.Conversation
import com.example.ndugu.feature.messaging.domain.model.Message

fun ConversationDto.toDomain(): Conversation {
    return Conversation(
        id = id,
        otherParticipantName = otherParticipantName,
        lastMessage = lastMessage,
        lastMessageTime = lastMessageTime,
        unreadCount = unreadCount
    )
}

fun MessageDto.toDomain(currentUserId: String?): Message {
    return Message(
        id = id,
        conversationId = conversationId,
        senderId = senderId,
        senderName = senderName,
        content = content,
        timestamp = timestamp,
        isRead = isRead,
        isFromMe = senderId == currentUserId
    )
}
