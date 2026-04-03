package com.example.ndugu.feature.wallet.presentation.transactiondetail

import com.example.ndugu.core.presentation.UiText
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionType

data class TransactionDetailState(
    val transactionId: String = "",
    val type: TransactionType = TransactionType.DEBIT,
    val amountKes: String = "",
    val counterpartyName: String = "",
    val counterpartyPhone: String = "",
    val formattedDate: String = "",
    val formattedTime: String = "12:45 PM", // Mocked for UI
    val memo: String? = null,
    val canRequestReversal: Boolean = false,
    val isLoading: Boolean = false,
    val error: UiText? = null,
    // Additions for detailed premium UI
    val balanceAfter: String = "KES 4,200.00",
    val merchantLocation: String = "Campus West Wing",
    val merchantId: String = "442",
    val isTrustedMerchant: Boolean = true,
    val merchantImageUrl: String? = null,
    val userProfileImageUrl: String? = null,
)

sealed interface TransactionDetailAction {
    data object OnBackClick : TransactionDetailAction
    data object OnRequestReversalClick : TransactionDetailAction
}

sealed interface TransactionDetailEvent {
    data object NavigateBack : TransactionDetailEvent
    data class NavigateToReversal(val transactionId: String) : TransactionDetailEvent
    data class ShowSnackbar(val message: UiText) : TransactionDetailEvent
}
