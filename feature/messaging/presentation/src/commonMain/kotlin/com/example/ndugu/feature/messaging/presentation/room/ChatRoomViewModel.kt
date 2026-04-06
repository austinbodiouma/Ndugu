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

    private val _state = MutableStateFlow(
        ChatRoomState(
            recipientName = "Alex Kamau",
            recipientAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD4uejtYlVdx0EbsVMXBYlVgXT0RafCGByr0c0_VOCW5sw0ik4fa8NYl-3oTTE-dW-n9xIjPL8IzwnggVnPMjCW407XZvmifxTBH-98EXEZYoW3ynyvvIkAYg7CaPXVPMGSFXnkJUzPoq7wfxLbmVbzP3auCcrskBAtVj3CCyp-MVKb7BuRHOhxbFjAnpirVacf_8o7QLDKinNmgDQxb6D2W2Qo4M0b8pkm7P1SWatrxOzAt3t9znXtn30O9Yp2cV31wDy5OJT7yD4",
            isOnline = true,
            contextItem = ChatContextItem(
                title = "Eco Economics Textbook",
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAS_8i_f3OtrphP4cQZpbREOe8n7WZiVVhGlPzgYpWjNcYyfvUjcUMGhpQIsiXhKbMF-dzFvol03DOhGx0r-jnPJUEnoTpQd8cSFVOsjGzvro005guludzmGDkHOv6jf97Ta6lTq0jlm5T3p-sCpq2Z7zP71xgctSBu-UQJrkcGMsCT-iRL_UCpw4R4ABhmTH8_1-EN88SZkmNZ0mOA2iAKg29EE-mDH2nMiQMbFWGBD-Ry66yQZKqXEgAH1GN_f3TqZPdTgm_otGs"
            ),
            messages = listOf(
                ChatMessage(
                    id = "1",
                    content = "Hey, is the textbook still available?",
                    timestamp = "10:24 AM",
                    isFromMe = false
                ),
                ChatMessage(
                    id = "2",
                    content = "Yes, it is! I can meet you at the library at 2 PM.",
                    timestamp = "10:26 AM",
                    isFromMe = true,
                    deliveryStatus = DeliveryStatus.READ
                ),
                ChatMessage(
                    id = "3",
                    content = "Does that work for you?",
                    timestamp = "10:26 AM",
                    isFromMe = true,
                    deliveryStatus = DeliveryStatus.READ
                ),
                ChatMessage(
                    id = "4",
                    content = "Perfect! See you then.",
                    timestamp = "10:28 AM",
                    isFromMe = false
                )
            )
        )
    )
    val state = _state.asStateFlow()

    private val _events = Channel<ChatRoomEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: ChatRoomAction) {
        when (action) {
            is ChatRoomAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ChatRoomEvent.NavigateBack) }
            }
            is ChatRoomAction.OnMessageInputChange -> {
                _state.update { it.copy(messageInput = action.newValue) }
            }
            is ChatRoomAction.OnSendMessage -> {
                val currentInput = _state.value.messageInput
                if (currentInput.isNotBlank()) {
                    val newMessage = ChatMessage(
                        id = (_state.value.messages.size + 1).toString(),
                        content = currentInput,
                        timestamp = "Now",
                        isFromMe = true,
                        deliveryStatus = DeliveryStatus.SENT
                    )
                    _state.update { 
                        it.copy(
                            messages = it.messages + newMessage,
                            messageInput = ""
                        )
                    }
                }
            }
            is ChatRoomAction.OnAddAttachmentClick -> {
                // TODO: Implement attachment selection
            }
            is ChatRoomAction.OnMoreOptionsClick -> {
                // TODO: Implement more options menu
            }
            is ChatRoomAction.OnContextItemClick -> {
                // TODO: Navigate to item details
            }
        }
    }
}
