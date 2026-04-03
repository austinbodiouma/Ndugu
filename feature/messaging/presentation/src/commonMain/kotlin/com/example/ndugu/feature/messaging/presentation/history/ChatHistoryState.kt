package com.example.ndugu.feature.messaging.presentation.history

import com.example.ndugu.core.presentation.UiText

data class ChatHistoryState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val selectedCategory: ChatCategory = ChatCategory.ALL,
    val chats: List<ChatItem> = emptyList(),
    val searchQuery: String = ""
)

enum class ChatCategory {
    ALL, BUYING, SELLING, GROUPS
}

data class ChatItem(
    val id: String,
    val senderName: String,
    val senderImageUrl: String? = null,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val deliveryStatus: DeliveryStatus? = null,
    val contextItem: ChatContextItem? = null
)

data class ChatContextItem(
    val title: String,
    val imageUrl: String
)

enum class DeliveryStatus {
    SENT, DELIVERED, READ
}

sealed interface ChatHistoryAction {
    data object OnBackClick : ChatHistoryAction
    data class OnCategorySelect(val category: ChatCategory) : ChatHistoryAction
    data class OnSearchQueryChange(val query: String) : ChatHistoryAction
    data class OnChatClick(val chatId: String) : ChatHistoryAction
    data object OnComposeNewChat : ChatHistoryAction
}

sealed interface ChatHistoryEvent {
    data object NavigateBack : ChatHistoryEvent
    data class NavigateToChat(val chatId: String) : ChatHistoryEvent
    data object NavigateToCompose : ChatHistoryEvent
}
