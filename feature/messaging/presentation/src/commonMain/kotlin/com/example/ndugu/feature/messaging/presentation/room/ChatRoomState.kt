package com.example.ndugu.feature.messaging.presentation.room

import com.example.ndugu.core.presentation.UiText

data class ChatRoomState(
    val isLoading: Boolean = false,
    val error: UiText? = null
)

sealed interface ChatRoomAction {
    data object OnBackClick : ChatRoomAction
}

sealed interface ChatRoomEvent {
    data object NavigateBack : ChatRoomEvent
}
