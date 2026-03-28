package com.example.ndugu.feature.wallet.presentation.budget

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

private val sampleBudgets = listOf(
    BudgetCategoryUi("Food & Drinks", "3,000", "1,800", 0.60f, false),
    BudgetCategoryUi("Transport", "1,000", "950", 0.95f, false),
    BudgetCategoryUi("Stationery", "500", "620", 1.24f, true),
    BudgetCategoryUi("Entertainment", "800", "200", 0.25f, false),
)

@Preview(name = "Budget — Normal", showBackground = true)
@Composable
private fun BudgetScreenPreview() {
    MaterialTheme {
        BudgetScreen(
            state = BudgetState(budgets = sampleBudgets),
            onAction = {},
        )
    }
}

@Preview(name = "Budget — Empty (no budgets set)", showBackground = true)
@Composable
private fun BudgetScreenEmptyPreview() {
    MaterialTheme {
        BudgetScreen(
            state = BudgetState(budgets = emptyList()),
            onAction = {},
        )
    }
}
