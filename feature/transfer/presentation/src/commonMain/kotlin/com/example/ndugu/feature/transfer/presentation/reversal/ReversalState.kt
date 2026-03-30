package com.example.ndugu.feature.transfer.presentation.reversal

import com.example.ndugu.core.presentation.UiText

data class ReversalState(
    val transactionId: String = "",
    val recipientName: String = "",
    val recipientAvatarUrl: String? = null,
    val amountKes: String = "",
    val dateFormatted: String = "",
    val timeRemainingSeconds: Int = 600, // 10 minutes default
    val reason: String = "",
    val isLoading: Boolean = false,
    val isSubmitted: Boolean = false,
    val error: UiText? = null,
)

sealed interface ReversalAction {
    data class OnReasonChange(val reason: String) : ReversalAction
    data object OnConfirmReversalClick : ReversalAction
    data object OnBackClick : ReversalAction
    data object OnCancelClick : ReversalAction
}

sealed interface ReversalEvent {
    data object NavigateBack : ReversalEvent
    data class ShowSnackbar(val message: UiText) : ReversalEvent
}
