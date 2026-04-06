package com.example.ndugu.feature.messaging.presentation.room

import com.example.ndugu.core.presentation.UiText

data class ChatRoomState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val recipientName: String = "",
    val recipientAvatarUrl: String? = null,
    val isOnline: Boolean = false,
    val contextItem: ChatContextItem? = null,
    val messages: List<ChatMessage> = emptyList(),
    val messageInput: String = ""
)

data class ChatMessage(
    val id: String,
    val content: String,
    val timestamp: String,
    val isFromMe: Boolean,
    val deliveryStatus: DeliveryStatus? = null
)

data class ChatContextItem(
    val title: String,
    val imageUrl: String
)

enum class DeliveryStatus {
    SENT, DELIVERED, READ
}

sealed interface ChatRoomAction {
    data object OnBackClick : ChatRoomAction
    data class OnMessageInputChange(val newValue: String) : ChatRoomAction
    data object OnSendMessage : ChatRoomAction
    data object OnAddAttachmentClick : ChatRoomAction
    data object OnMoreOptionsClick : ChatRoomAction
    data object OnContextItemClick : ChatRoomAction
}

sealed interface ChatRoomEvent {
    data object NavigateBack : ChatRoomEvent
}
