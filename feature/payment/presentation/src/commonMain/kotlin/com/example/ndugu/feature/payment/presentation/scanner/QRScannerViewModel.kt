package com.example.ndugu.feature.payment.presentation.scanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QRScannerViewModel : ViewModel() {

    private val _state = MutableStateFlow(QRScannerState())
    val state = _state.asStateFlow()

    private val _events = Channel<QRScannerEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: QRScannerAction) {
        when (action) {
            is QRScannerAction.OnBackClick -> {
                viewModelScope.launch { _events.send(QRScannerEvent.NavigateBack) }
            }
        }
    }
}
