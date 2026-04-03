package com.example.ndugu.feature.payment.presentation.scanner

import com.example.ndugu.core.presentation.UiText

data class QRScannerState(
    val isLoading: Boolean = false,
    val error: UiText? = null
)

sealed interface QRScannerAction {
    data object OnBackClick : QRScannerAction
}

sealed interface QRScannerEvent {
    data object NavigateBack : QRScannerEvent
}
