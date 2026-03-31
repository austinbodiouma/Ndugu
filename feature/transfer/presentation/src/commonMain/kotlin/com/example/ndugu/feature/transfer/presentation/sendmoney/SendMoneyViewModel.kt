package com.example.ndugu.feature.transfer.presentation.sendmoney

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SendMoneyViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val recipientPhone: String = checkNotNull(savedStateHandle["recipientPhone"])
    private val recipientName: String = savedStateHandle["recipientName"] ?: ""

    private val _state = MutableStateFlow(
        SendMoneyState(
            recipientPhone = recipientPhone,
            recipientName = recipientName,
        )
    )
    val state = _state.asStateFlow()

    private val _events = Channel<SendMoneyEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: SendMoneyAction) {
        when (action) {
            is SendMoneyAction.OnKeypadDigit -> _state.update { 
                val current = it.amountKes
                if (current == "0.00" || current == "0") {
                    it.copy(amountKes = action.digit)
                } else {
                    // Prevent multiple dots and limit to 2 decimal places
                    if (action.digit == "." && current.contains(".")) return@update it
                    if (current.contains(".") && current.substringAfter(".").length >= 2) return@update it
                    it.copy(amountKes = current + action.digit)
                }
            }
            is SendMoneyAction.OnKeypadBackspace -> _state.update {
                val current = it.amountKes
                if (current.length <= 1) {
                    it.copy(amountKes = "0.00")
                } else {
                    it.copy(amountKes = current.dropLast(1))
                }
            }
            is SendMoneyAction.OnAmountChange -> _state.update {
                it.copy(amountKes = action.amount, amountError = null)
            }
            is SendMoneyAction.OnMemoChange -> _state.update {
                it.copy(memo = action.memo)
            }
            is SendMoneyAction.OnReviewClick -> _state.update {
                it.copy(isConfirmStep = true)
            }
            is SendMoneyAction.OnEditClick -> _state.update {
                it.copy(isConfirmStep = false)
            }
            is SendMoneyAction.OnConfirmSendClick -> sendMoney()
            is SendMoneyAction.OnBackClick -> {
                viewModelScope.launch { _events.send(SendMoneyEvent.NavigateBack) }
            }
        }
    }

    private fun sendMoney() {
        // TODO: inject and call SendMoneyUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            // stub — navigate to success
            _events.send(SendMoneyEvent.NavigateToSuccess(transactionId = "stub-tx-id"))
            _state.update { it.copy(isLoading = false) }
        }
    }
}
