package com.example.ndugu.feature.wallet.presentation.dashboard

import com.example.ndugu.core.presentation.UiText

data class WalletDashboardState(
    val balanceKes: String = "",
    val isBalanceVisible: Boolean = true,
    val verificationStatus: VerificationStatus = VerificationStatus.PENDING,
    val recentTransactions: List<TransactionUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)

enum class VerificationStatus { PENDING, VERIFIED, REJECTED }

data class TransactionUi(
    val id: String,
    val type: TransactionType,
    val counterpartyName: String,
    val amountKes: String,       // formatted, e.g. "+ KES 500"
    val formattedDate: String,
    val memo: String?,
)

enum class TransactionType { CREDIT, DEBIT }

sealed interface WalletDashboardAction {
    data object OnRefresh : WalletDashboardAction
    data object OnToggleBalanceVisibility : WalletDashboardAction
    data object OnTopUpClick : WalletDashboardAction
    data object OnSendMoneyClick : WalletDashboardAction
    data object OnScanQrClick : WalletDashboardAction
    data object OnSeeAllTransactionsClick : WalletDashboardAction
    data class OnTransactionClick(val transactionId: String) : WalletDashboardAction
}

sealed interface WalletDashboardEvent {
    data object NavigateToTopUp : WalletDashboardEvent
    data object NavigateToSendMoney : WalletDashboardEvent
    data object NavigateToScanQr : WalletDashboardEvent
    data object NavigateToTransactionHistory : WalletDashboardEvent
    data class NavigateToTransactionDetail(val transactionId: String) : WalletDashboardEvent
    data class ShowSnackbar(val message: UiText) : WalletDashboardEvent
}
