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
        // TODO: inject and call GetWalletBalanceUseCase + GetTransactionHistoryUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
