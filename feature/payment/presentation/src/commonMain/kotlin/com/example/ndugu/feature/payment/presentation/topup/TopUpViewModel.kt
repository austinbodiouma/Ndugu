package com.example.ndugu.feature.payment.presentation.topup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopUpViewModel : ViewModel() {

    private val _state = MutableStateFlow(TopUpState())
    val state = _state.asStateFlow()

    private val _events = Channel<TopUpEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TopUpAction) {
        when (action) {
            is TopUpAction.OnBackClick -> {
                viewModelScope.launch { _events.send(TopUpEvent.NavigateBack) }
            }
        }
    }
}
