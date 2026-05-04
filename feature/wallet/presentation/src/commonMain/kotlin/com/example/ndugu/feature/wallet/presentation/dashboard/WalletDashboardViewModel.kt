package com.example.ndugu.feature.wallet.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ndugu.core.domain.util.onSuccess
import com.example.ndugu.feature.wallet.domain.repository.WalletRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletDashboardViewModel(
    private val walletRepository: WalletRepository
) : ViewModel() {

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
            
            walletRepository.getWallet().onSuccess { wallet ->
                _state.update { it.copy(
                    balanceKes = "${wallet.currency} ${wallet.balance}",
                    isLoading = false
                ) }
            }

            walletRepository.getTransactions().onSuccess { transactions ->
                // Map domain transactions to UI models
                // This is a simplified mapping for now
                _state.update { it.copy(
                    recentTransactions = transactions.take(5).map { tx ->
                        TransactionUi(
                            id = tx.id,
                            type = if (tx.type.name == "CREDIT") TransactionType.CREDIT else TransactionType.DEBIT,
                            iconType = TransactionIconType.PAYMENTS, // Default for now
                            counterpartyName = tx.counterpartyName,
                            amountKes = "${if(tx.type.name == "CREDIT") "+" else "-"} KES ${tx.amount}",
                            formattedDate = tx.createdAt,
                            memo = tx.memo
                        )
                    }
                ) }
            }
            
            walletRepository.getBudgets().onSuccess { budgets ->
                _state.update { it.copy(
                    budgets = budgets.map { b ->
                        BudgetUi(
                            category = when(b.category) {
                                "FOOD" -> BudgetCategory.FOOD
                                "TRANSPORT" -> BudgetCategory.TRANSPORT
                                else -> BudgetCategory.UTILITIES
                            },
                            progress = if(b.limitAmount > 0) (b.spentAmount / b.limitAmount).toFloat() else 0f
                        )
                    }
                ) }
            }
            
            _state.update { it.copy(isLoading = false) }
        }
    }
}
