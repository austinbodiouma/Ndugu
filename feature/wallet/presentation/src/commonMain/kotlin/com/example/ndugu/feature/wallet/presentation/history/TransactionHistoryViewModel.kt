package com.example.ndugu.feature.wallet.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionIconType
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionType
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionHistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(TransactionHistoryState())
    
    private val allTransactions = listOf(
        TransactionUi(
            id = "1",
            type = TransactionType.DEBIT,
            iconType = TransactionIconType.RESTAURANT,
            counterpartyName = "Student Cafeteria",
            amountKes = "- $12.50",
            formattedDate = "Today, 12:45 PM",
            memo = "Lunch & Beverages",
            dateSection = "Today"
        ),
        TransactionUi(
            id = "2",
            type = TransactionType.CREDIT,
            iconType = TransactionIconType.ADD_CARD,
            counterpartyName = "Balance Top-up",
            amountKes = "+ $50.00",
            formattedDate = "Today, 09:15 AM",
            memo = "Visa **** 4421",
            dateSection = "Today"
        ),
        TransactionUi(
            id = "3",
            type = TransactionType.CREDIT,
            iconType = TransactionIconType.PERSON,
            counterpartyName = "From Sarah J.",
            amountKes = "+ $15.00",
            formattedDate = "Today, 08:30 AM",
            memo = "Group Project Share",
            dateSection = "Today"
        ),
        TransactionUi(
            id = "4",
            type = TransactionType.DEBIT,
            iconType = TransactionIconType.MENU_BOOK,
            counterpartyName = "Campus Bookstore",
            amountKes = "- $84.20",
            formattedDate = "Yesterday, 4:20 PM",
            memo = "Textbooks & Stationery",
            dateSection = "Yesterday"
        ),
        TransactionUi(
            id = "5",
            type = TransactionType.DEBIT,
            iconType = TransactionIconType.LOCAL_PRINTSHOP,
            counterpartyName = "Print Services",
            amountKes = "- $3.15",
            formattedDate = "Yesterday, 11:05 AM",
            memo = "Library Hall 2",
            dateSection = "Yesterday"
        ),
        TransactionUi(
            id = "6",
            type = TransactionType.DEBIT,
            iconType = TransactionIconType.PAYMENTS,
            counterpartyName = "Tuition Installment",
            amountKes = "- $1,200.00",
            formattedDate = "Oct 22, 2023, 03:30 PM",
            memo = "Fall Semester 2023",
            dateSection = "Oct 22, 2023"
        )
    )

    val state = combine(
        _state,
        _state.update { it.copy(transactions = allTransactions) }.let { _state }
    ) { state, _ ->
        val filtered = allTransactions.filter { tx ->
            val matchesSearch = tx.counterpartyName.contains(state.searchQuery, ignoreCase = true) ||
                    (tx.memo?.contains(state.searchQuery, ignoreCase = true) == true)
            
            val matchesFilter = when (state.selectedFilter) {
                TransactionFilter.ALL -> true
                TransactionFilter.SENT -> tx.type == TransactionType.DEBIT
                TransactionFilter.RECEIVED -> tx.type == TransactionType.CREDIT
                TransactionFilter.TOP_UPS -> tx.iconType == TransactionIconType.ADD_CARD
                TransactionFilter.PAYMENTS -> tx.iconType == TransactionIconType.PAYMENTS || tx.iconType == TransactionIconType.RESTAURANT
            }
            
            matchesSearch && matchesFilter
        }
        
        state.copy(
            transactions = filtered,
            groupedTransactions = filtered.groupBy { it.dateSection ?: "Other" }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TransactionHistoryState())

    private val _events = Channel<TransactionHistoryEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TransactionHistoryAction) {
        when (action) {
            is TransactionHistoryAction.OnRefresh -> { /* Refresh logic */ }
            is TransactionHistoryAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
            }
            is TransactionHistoryAction.OnFilterSelect -> {
                _state.update { it.copy(selectedFilter = action.filter) }
            }
            is TransactionHistoryAction.OnTransactionClick -> {
                viewModelScope.launch {
                    _events.send(TransactionHistoryEvent.NavigateToDetail(action.transactionId))
                }
            }
            is TransactionHistoryAction.OnBackClick -> {
                viewModelScope.launch { _events.send(TransactionHistoryEvent.NavigateBack) }
            }
        }
    }
}
