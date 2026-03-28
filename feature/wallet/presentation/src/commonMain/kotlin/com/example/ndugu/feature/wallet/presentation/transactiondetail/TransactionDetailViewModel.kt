package com.example.ndugu.feature.wallet.presentation.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val transactionId: String = checkNotNull(savedStateHandle["transactionId"])

    private val _state = MutableStateFlow(TransactionDetailState(transactionId = transactionId))
    val state = _state.asStateFlow()

    private val _events = Channel<TransactionDetailEvent>()
    val events = _events.receiveAsFlow()

    init { load() }

    fun onAction(action: TransactionDetailAction) {
        when (action) {
            is TransactionDetailAction.OnBackClick -> {
                viewModelScope.launch { _events.send(TransactionDetailEvent.NavigateBack) }
            }
            is TransactionDetailAction.OnRequestReversalClick -> {
                viewModelScope.launch {
                    _events.send(TransactionDetailEvent.NavigateToReversal(transactionId))
                }
            }
        }
    }

    private fun load() {
        // TODO: inject and call GetTransactionDetailUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
