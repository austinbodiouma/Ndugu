package com.example.ndugu.feature.wallet.presentation.budget

import com.example.ndugu.core.presentation.UiText

data class BudgetState(
    val budgets: List<BudgetCategoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val isAddEditSheetOpen: Boolean = false,
    val addEditForm: BudgetFormState = BudgetFormState(),
)

data class BudgetFormState(
    val id: String? = null,
    val category: String? = null,
    val amount: String = "0.00",
    val isAlertEnabled: Boolean = true,
    val isRenewEnabled: Boolean = false,
)

data class BudgetCategoryUi(
    val category: String,
    val budgetKes: String,
    val spentKes: String,
    val progressFraction: Float,  // 0f–1f+
    val isOverBudget: Boolean,
)

sealed interface BudgetAction {
    data object OnRefresh : BudgetAction
    data object OnBackClick : BudgetAction
    data class OnEditBudget(val budget: BudgetCategoryUi) : BudgetAction
    data class OnSetBudget(val category: String, val amountKes: Double) : BudgetAction
    
    // Form actions
    data object OnAddBudgetClick : BudgetAction
    data object OnDismissSheet : BudgetAction
    data class OnFormCategorySelect(val category: String) : BudgetAction
    data class OnFormAmountChange(val amount: String) : BudgetAction
    data class OnFormAlertToggle(val enabled: Boolean) : BudgetAction
    data class OnFormRenewToggle(val enabled: Boolean) : BudgetAction
    data object OnSaveBudget : BudgetAction
}

sealed interface BudgetEvent {
    data object NavigateBack : BudgetEvent
    data class ShowSnackbar(val message: UiText) : BudgetEvent
}
