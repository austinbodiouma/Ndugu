package com.example.ndugu.feature.messaging.presentation.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatRoomViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChatRoomState())
    val state = _state.asStateFlow()

    private val _events = Channel<ChatRoomEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ChatRoomAction) {
        when (action) {
            is ChatRoomAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ChatRoomEvent.NavigateBack) }
            }
        }
    }
}
