package com.example.ndugu.feature.wallet.presentation.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BudgetRoot(
    onNavigateBack: () -> Unit,
    viewModel: BudgetViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is BudgetEvent.NavigateBack -> onNavigateBack()
            is BudgetEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    BudgetScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    state: BudgetState,
    onAction: (BudgetAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spending Budgets") },
                navigationIcon = {
                    IconButton(onClick = { onAction(BudgetAction.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            items(state.budgets, key = { it.category }) { budget ->
                BudgetCategoryItem(
                    budget = budget,
                    onEdit = { onAction(BudgetAction.OnEditBudget(budget.category)) },
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun BudgetCategoryItem(
    budget: BudgetCategoryUi,
    onEdit: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(budget.category, style = MaterialTheme.typography.titleSmall)
            TextButton(onClick = onEdit) { Text("Edit") }
        }
        LinearProgressIndicator(
            progress = { budget.progressFraction.coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth(),
            color = if (budget.isOverBudget) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "KES ${budget.spentKes} of KES ${budget.budgetKes}",
            style = MaterialTheme.typography.bodySmall,
            color = if (budget.isOverBudget) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
