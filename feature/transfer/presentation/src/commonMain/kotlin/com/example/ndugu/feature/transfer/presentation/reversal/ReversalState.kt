package com.example.ndugu.feature.transfer.presentation.reversal

import com.example.ndugu.core.presentation.UiText

data class ReversalState(
    val transactionId: String = "",
    val recipientName: String = "",
    val amountKes: String = "",
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val error: UiText? = null,
)

sealed interface ReversalAction {
    data object OnConfirmReversalClick : ReversalAction
    data object OnBackClick : ReversalAction
}

sealed interface ReversalEvent {
    data object NavigateBack : ReversalEvent
    data class ShowSnackbar(val message: UiText) : ReversalEvent
}
