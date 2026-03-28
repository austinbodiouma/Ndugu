package com.example.ndugu.feature.wallet.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionHistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(TransactionHistoryState())
    val state = _state.asStateFlow()

    private val _events = Channel<TransactionHistoryEvent>()
    val events = _events.receiveAsFlow()

    init { load() }

    fun onAction(action: TransactionHistoryAction) {
        when (action) {
            is TransactionHistoryAction.OnRefresh -> load()
            is TransactionHistoryAction.OnTransactionClick -> {
                viewModelScope.launch {
                    _events.send(TransactionHistoryEvent.NavigateToDetail(action.transactionId))
                }
            }
            is TransactionHistoryAction.OnBackClick -> {
                viewModelScope.launch { _events.send(TransactionHistoryEvent.NavigateBack) }
            }
        }
    }

    private fun load() {
        // TODO: inject and call GetTransactionHistoryUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
