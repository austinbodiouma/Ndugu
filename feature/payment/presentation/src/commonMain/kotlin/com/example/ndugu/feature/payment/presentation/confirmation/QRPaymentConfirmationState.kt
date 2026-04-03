package com.example.ndugu.feature.payment.presentation.confirmation

import com.example.ndugu.core.presentation.UiText

data class QRPaymentConfirmationState(
    val isLoading: Boolean = false,
    val error: UiText? = null
)

sealed interface QRPaymentConfirmationAction {
    data object OnBackClick : QRPaymentConfirmationAction
}

sealed interface QRPaymentConfirmationEvent {
    data object NavigateBack : QRPaymentConfirmationEvent
}
