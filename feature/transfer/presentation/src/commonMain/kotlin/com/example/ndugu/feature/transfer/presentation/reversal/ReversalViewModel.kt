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
            is ReversalAction.OnReasonChange -> {
                _state.update { it.copy(reason = action.reason) }
            }
            is ReversalAction.OnConfirmReversalClick -> requestReversal()
            is ReversalAction.OnBackClick, ReversalAction.OnCancelClick -> {
                viewModelScope.launch { _events.send(ReversalEvent.NavigateBack) }
            }
        }
    }

    private fun loadTransactionSummary() {
        // TODO: replace with real data loading from Repository
        _state.update {
            it.copy(
                recipientName = "Campus Bookstore",
                recipientAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDh1-F7KTnhmKq8zImNfVia-I6ffjmENav8c_GCSsqCYzIyXUrtRANgsDvygEMCrXe6Ftt-nO6NYMe9LVgka6AsvvUy7WlkbAIHKryxy8gmUaRzZwKLauW6yY_tXJU68hh0kiCJ5gcWFFVs7bCSk5mL1eQcMQz0836aHvRxSPA5pJTvgHBMegK8pQBHfoz5wYcVlcnsaS92_0RtAbAlLmdecbdMSexvW8VKFn_dmjY9caiD59MaA_4xoUDR4KqigbZSohOrrQQRKiI",
                amountKes = "42.50",
                dateFormatted = "Today, 11:42 AM",
                timeRemainingSeconds = 534 // 08:54 equivalent
            )
        }
    }

    private fun requestReversal() {
        // TODO: inject and call RequestReversalUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            // Mock network delay
            kotlinx.coroutines.delay(1500)
            _state.update { it.copy(isLoading = false, isSubmitted = true) }
            _events.send(ReversalEvent.NavigateBack)
        }
    }
}
