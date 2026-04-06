package com.example.ndugu.feature.wallet.presentation.qrscanner

import com.example.ndugu.core.presentation.UiText

data class QrScannerState(
    val isScanning: Boolean = true,
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val lastScannedCode: String? = null
)

sealed interface QrScannerAction {
    data object OnBackClick : QrScannerAction
    data class OnCodeScanned(val code: String) : QrScannerAction
    data object OnToggleFlashlight : QrScannerAction
    data object OnRetryClick : QrScannerAction
}

sealed interface QrScannerEvent {
    data object NavigateBack : QrScannerEvent
    data class NavigateToPayment(val receiverId: String, val amount: Double? = null) : QrScannerEvent
    data class ShowError(val message: UiText) : QrScannerEvent
}
