package com.example.ndugu.feature.wallet.presentation.budget

import com.example.ndugu.core.presentation.UiText

data class BudgetState(
    val budgets: List<BudgetCategoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
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
    data class OnEditBudget(val category: String) : BudgetAction
    data class OnSetBudget(val category: String, val amountKes: Double) : BudgetAction
}

sealed interface BudgetEvent {
    data object NavigateBack : BudgetEvent
    data class ShowSnackbar(val message: UiText) : BudgetEvent
}
