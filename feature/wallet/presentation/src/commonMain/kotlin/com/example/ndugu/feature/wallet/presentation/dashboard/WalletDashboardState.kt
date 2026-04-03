package com.example.ndugu.feature.wallet.presentation.dashboard

import com.example.ndugu.core.presentation.UiText

data class WalletDashboardState(
    val userName: String = "",
    val profileImageUrl: String? = null,
    val balanceKes: String = "",
    val balanceChangePercentage: String = "", // e.g. "+2.4%"
    val isBalanceVisible: Boolean = true,
    val verificationStatus: VerificationStatus = VerificationStatus.PENDING,
    val recentTransactions: List<TransactionUi> = emptyList(),
    val budgets: List<BudgetUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)

data class BudgetUi(
    val category: BudgetCategory,
    val percentage: Float, // 0.7f for 70%
)

enum class BudgetCategory { FOOD, TRANSPORT, UTILITIES }

enum class VerificationStatus { PENDING, VERIFIED, REJECTED }

data class TransactionUi(
    val id: String,
    val type: TransactionType,
    val iconType: TransactionIconType,
    val counterpartyName: String,
    val amountKes: String,       // formatted, e.g. "+ KES 500"
    val formattedDate: String,
    val memo: String?,
    val dateSection: String? = null,
)

enum class TransactionType { CREDIT, DEBIT }

enum class TransactionIconType { PERSON, BANK, RESTAURANT, SHOPPING_BAG, PAYMENTS, ADD_CARD, MENU_BOOK, LOCAL_PRINTSHOP }

sealed interface WalletDashboardAction {
    data object OnRefresh : WalletDashboardAction
    data object OnToggleBalanceVisibility : WalletDashboardAction
    data object OnTopUpClick : WalletDashboardAction
    data object OnSendMoneyClick : WalletDashboardAction
    data object OnScanQrClick : WalletDashboardAction
    data object OnRequestMoneyClick : WalletDashboardAction
    data object OnSeeAllTransactionsClick : WalletDashboardAction
    data class OnTransactionClick(val transactionId: String) : WalletDashboardAction
}

sealed interface WalletDashboardEvent {
    data object NavigateToTopUp : WalletDashboardEvent
    data object NavigateToSendMoney : WalletDashboardEvent
    data object NavigateToScanQr : WalletDashboardEvent
    data object NavigateToRequestMoney : WalletDashboardEvent
    data object NavigateToTransactionHistory : WalletDashboardEvent
    data class NavigateToTransactionDetail(val transactionId: String) : WalletDashboardEvent
    data class ShowSnackbar(val message: UiText) : WalletDashboardEvent
}

