package com.example.ndugu.feature.transfer.presentation.reversal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReversalViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionId: String = checkNotNull(savedStateHandle["transactionId"])

    private val _state = MutableStateFlow(ReversalState(transactionId = transactionId))
    val state = _state.asStateFlow()

    private val _events = Channel<ReversalEvent>()
    val events = _events.receiveAsFlow()

    init { loadTransactionSummary() }

    fun onAction(action: ReversalAction) {
        when (action) {
            is ReversalAction.OnConfirmReversalClick -> requestReversal()
            is ReversalAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ReversalEvent.NavigateBack) }
            }
        }
    }

    private fun loadTransactionSummary() {
        // TODO: load transaction summary for display
    }

    private fun requestReversal() {
        // TODO: inject and call RequestReversalUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false, isSubmitted = true) }
            _events.send(ReversalEvent.NavigateBack)
        }
    }
}
