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
            is BudgetAction.OnEditBudget -> {
                _state.update { it.copy(
                    isAddEditSheetOpen = true,
                    addEditForm = BudgetFormState(
                        id = action.budget.category,
                        category = action.budget.category,
                        amount = action.budget.budgetKes.replace(",", ""),
                        isAlertEnabled = true, // Default
                        isRenewEnabled = false // Default
                    )
                ) }
            }
            is BudgetAction.OnSetBudget -> setBudget(action.category, action.amountKes)
            
            BudgetAction.OnAddBudgetClick -> {
                _state.update { it.copy(
                    isAddEditSheetOpen = true,
                    addEditForm = BudgetFormState()
                ) }
            }
            BudgetAction.OnDismissSheet -> {
                _state.update { it.copy(isAddEditSheetOpen = false) }
            }
            is BudgetAction.OnFormAmountChange -> {
                _state.update { it.copy(
                    addEditForm = it.addEditForm.copy(amount = action.amount)
                ) }
            }
            is BudgetAction.OnFormCategorySelect -> {
                _state.update { it.copy(
                    addEditForm = it.addEditForm.copy(category = action.category)
                ) }
            }
            is BudgetAction.OnFormAlertToggle -> {
                _state.update { it.copy(
                    addEditForm = it.addEditForm.copy(isAlertEnabled = action.enabled)
                ) }
            }
            is BudgetAction.OnFormRenewToggle -> {
                _state.update { it.copy(
                    addEditForm = it.addEditForm.copy(isRenewEnabled = action.enabled)
                ) }
            }
            BudgetAction.OnSaveBudget -> {
                saveBudget()
            }
        }
    }

    private fun saveBudget() {
        val form = _state.value.addEditForm
        val amount = form.amount.toDoubleOrNull() ?: 0.0
        val category = form.category ?: return
        
        setBudget(category, amount)
        _state.update { it.copy(isAddEditSheetOpen = false) }
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
