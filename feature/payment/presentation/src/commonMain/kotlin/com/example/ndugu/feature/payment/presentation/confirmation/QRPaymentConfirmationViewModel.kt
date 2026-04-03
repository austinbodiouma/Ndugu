package com.example.ndugu.feature.payment.presentation.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QRPaymentConfirmationViewModel : ViewModel() {

    private val _state = MutableStateFlow(QRPaymentConfirmationState())
    val state = _state.asStateFlow()

    private val _events = Channel<QRPaymentConfirmationEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: QRPaymentConfirmationAction) {
        when (action) {
            is QRPaymentConfirmationAction.OnBackClick -> {
                viewModelScope.launch { _events.send(QRPaymentConfirmationEvent.NavigateBack) }
            }
        }
    }
}
