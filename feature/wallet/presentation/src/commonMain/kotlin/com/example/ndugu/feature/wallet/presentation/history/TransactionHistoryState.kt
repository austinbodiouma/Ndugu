package com.example.ndugu.feature.wallet.presentation.history

import com.example.ndugu.core.presentation.UiText
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionUi

data class TransactionHistoryState(
    val transactions: List<TransactionUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)

sealed interface TransactionHistoryAction {
    data object OnRefresh : TransactionHistoryAction
    data class OnTransactionClick(val transactionId: String) : TransactionHistoryAction
    data object OnBackClick : TransactionHistoryAction
}

sealed interface TransactionHistoryEvent {
    data class NavigateToDetail(val transactionId: String) : TransactionHistoryEvent
    data object NavigateBack : TransactionHistoryEvent
    data class ShowSnackbar(val message: UiText) : TransactionHistoryEvent
}
