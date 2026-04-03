package com.example.ndugu.feature.marketplace.presentation.dispute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisputeViewModel : ViewModel() {

    private val _state = MutableStateFlow(DisputeState())
    val state = _state.asStateFlow()

    private val _events = Channel<DisputeEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: DisputeAction) {
        when (action) {
            is DisputeAction.OnBackClick -> {
                viewModelScope.launch { _events.send(DisputeEvent.NavigateBack) }
            }
        }
    }
}
