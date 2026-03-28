package com.example.ndugu.feature.wallet.presentation.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BudgetViewModel : ViewModel() {

    private val _state = MutableStateFlow(BudgetState())
    val state = _state.asStateFlow()

    private val _events = Channel<BudgetEvent>()
    val events = _events.receiveAsFlow()

    init { load() }

    fun onAction(action: BudgetAction) {
        when (action) {
            is BudgetAction.OnRefresh -> load()
            is BudgetAction.OnBackClick -> {
                viewModelScope.launch { _events.send(BudgetEvent.NavigateBack) }
            }
            is BudgetAction.OnEditBudget -> { /* open bottom sheet / dialog */ }
            is BudgetAction.OnSetBudget -> setBudget(action.category, action.amountKes)
        }
    }

    private fun load() {
        // TODO: inject and call GetBudgetProgressUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun setBudget(category: String, amountKes: Double) {
        // TODO: inject and call SetBudgetUseCase
    }
}
