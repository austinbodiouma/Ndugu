package com.example.ndugu.feature.auth.presentation.verificationpending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class VerificationPendingViewModel : ViewModel() {

    private val _state = MutableStateFlow(VerificationPendingState())
    val state = _state.asStateFlow()

    private val _events = Channel<VerificationPendingEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: VerificationPendingAction) {
        when (action) {
            is VerificationPendingAction.OnBackClick -> {
                viewModelScope.launch { _events.send(VerificationPendingEvent.NavigateBack) }
            }
            is VerificationPendingAction.OnCheckStatusClick -> {
                // TODO: Check verification status from backend
            }
            is VerificationPendingAction.OnLogoutClick -> {
                viewModelScope.launch { _events.send(VerificationPendingEvent.NavigateBack) }
            }
        }
    }
}
