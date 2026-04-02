package com.example.ndugu.feature.wallet.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletDashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(WalletDashboardState())
    val state = _state.asStateFlow()

    private val _events = Channel<WalletDashboardEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadDashboard()
    }

    fun onAction(action: WalletDashboardAction) {
        when (action) {
            is WalletDashboardAction.OnRefresh -> loadDashboard()
            is WalletDashboardAction.OnToggleBalanceVisibility -> {
                _state.update { it.copy(isBalanceVisible = !it.isBalanceVisible) }
            }
            is WalletDashboardAction.OnTopUpClick -> {
                viewModelScope.launch { _events.send(WalletDashboardEvent.NavigateToTopUp) }
            }
            is WalletDashboardAction.OnSendMoneyClick -> {
                viewModelScope.launch { _events.send(WalletDashboardEvent.NavigateToSendMoney) }
            }
            is WalletDashboardAction.OnScanQrClick -> {
                viewModelScope.launch { _events.send(WalletDashboardEvent.NavigateToScanQr) }
            }
            is WalletDashboardAction.OnRequestMoneyClick -> {
                viewModelScope.launch { _events.send(WalletDashboardEvent.NavigateToRequestMoney) }
            }
            is WalletDashboardAction.OnSeeAllTransactionsClick -> {
                viewModelScope.launch { _events.send(WalletDashboardEvent.NavigateToTransactionHistory) }
            }
            is WalletDashboardAction.OnTransactionClick -> {
                viewModelScope.launch {
                    _events.send(WalletDashboardEvent.NavigateToTransactionDetail(action.transactionId))
                }
            }
        }
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            // Mocking data based on the premium UI mockup
            _state.update { it.copy(
                userName = "Alex",
                profileImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFRQDVTPWXg_HPiz4eQjInvX1_s1ZrsRpwkEo96eYvQxakF0FlyIATR47BbFLh4kaJ19WBK4FPgjlyVVdg7HsrTUyLfRHCwpEZoAMuczbPb_2cgjpVExiXxUmAeFlhaanv_wv6qMbr41S0wRDrlvH2cm4QvjY7R_UpYCfsDzYFiI0LVNClepxGgPrT5EfnC3VC_kGNftnu_kc4hhwIjM8GsUtkKf6PeSgXNX9TC9zcg3khvN3tatE3iCxpmgkhcwDiVBCMvMk10oY",
                balanceKes = "12,450.00",
                balanceChangePercentage = "+2.4% from last month",
                recentTransactions = listOf(
                    TransactionUi(
                        id = "1",
                        type = TransactionType.DEBIT,
                        iconType = TransactionIconType.PERSON,
                        counterpartyName = "Sent to James",
                        amountKes = "- KES 450.00",
                        formattedDate = "Today, 10:45 AM",
                        memo = null
                    ),
                    TransactionUi(
                        id = "2",
                        type = TransactionType.CREDIT,
                        iconType = TransactionIconType.BANK,
                        counterpartyName = "M-Pesa Top Up",
                        amountKes = "+ KES 2,000.00",
                        formattedDate = "Yesterday, 4:20 PM",
                        memo = null
                    ),
                    TransactionUi(
                        id = "3",
                        type = TransactionType.DEBIT,
                        iconType = TransactionIconType.RESTAURANT,
                        counterpartyName = "Student Cafeteria",
                        amountKes = "- KES 180.00",
                        formattedDate = "Yesterday, 1:15 PM",
                        memo = null
                    ),
                    TransactionUi(
                        id = "4",
                        type = TransactionType.DEBIT,
                        iconType = TransactionIconType.SHOPPING_BAG,
                        counterpartyName = "Campus Bookstore",
                        amountKes = "- KES 1,200.00",
                        formattedDate = "24 Oct, 11:30 AM",
                        memo = null
                    ),
                    TransactionUi(
                        id = "5",
                        type = TransactionType.CREDIT,
                        iconType = TransactionIconType.PAYMENTS,
                        counterpartyName = "Salary Deposit",
                        amountKes = "+ KES 5,000.00",
                        formattedDate = "23 Oct, 9:00 AM",
                        memo = null
                    )
                ),
                budgets = listOf(
                    BudgetUi(BudgetCategory.FOOD, 0.70f),
                    BudgetUi(BudgetCategory.TRANSPORT, 0.30f),
                    BudgetUi(BudgetCategory.UTILITIES, 0.53f)
                ),
                isLoading = false
            ) }
        }
    }

}
