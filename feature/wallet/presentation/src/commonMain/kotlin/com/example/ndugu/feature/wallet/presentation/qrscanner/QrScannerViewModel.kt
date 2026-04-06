package com.example.ndugu.feature.wallet.presentation.qrscanner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class QrScannerViewModel : ViewModel() {

    private val _state = MutableStateFlow(QrScannerState())
    val state = _state.asStateFlow()

    private val _events = Channel<QrScannerEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: QrScannerAction) {
        when (action) {
            is QrScannerAction.OnBackClick -> {
                viewModelScope.launch { _events.send(QrScannerEvent.NavigateBack) }
            }
            is QrScannerAction.OnCodeScanned -> {
                processScannedCode(action.code)
            }
            else -> { /* TODO */ }
        }
    }

    private fun processScannedCode(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, isScanning = false) }
            // Mock processing
            if (code.startsWith("ndugu://pay/")) {
                val receiverId = code.removePrefix("ndugu://pay/")
                _events.send(QrScannerEvent.NavigateToPayment(receiverId))
            } else {
                _state.update { it.copy(isLoading = false, isScanning = true) }
                // Handle invalid code
            }
        }
    }
}
