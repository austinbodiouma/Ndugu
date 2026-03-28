package com.example.ndugu.feature.wallet.presentation.dashboard

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ndugu.core.presentation.ObserveAsEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WalletDashboardRoot(
    onNavigateToTopUp: () -> Unit,
    onNavigateToSendMoney: () -> Unit,
    onNavigateToScanQr: () -> Unit,
    onNavigateToTransactionHistory: () -> Unit,
    onNavigateToTransactionDetail: (String) -> Unit,
    viewModel: WalletDashboardViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is WalletDashboardEvent.NavigateToTopUp -> onNavigateToTopUp()
            is WalletDashboardEvent.NavigateToSendMoney -> onNavigateToSendMoney()
            is WalletDashboardEvent.NavigateToScanQr -> onNavigateToScanQr()
            is WalletDashboardEvent.NavigateToTransactionHistory -> onNavigateToTransactionHistory()
            is WalletDashboardEvent.NavigateToTransactionDetail -> onNavigateToTransactionDetail(event.transactionId)
            is WalletDashboardEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    WalletDashboardScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletDashboardScreen(
    state: WalletDashboardState,
    onAction: (WalletDashboardAction) -> Unit,
) {
    if (state.isLoading) {
        CircularProgressIndicator(Modifier.fillMaxSize().wrapContentSize())
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        // Balance Card
        item {
            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Your Balance", style = MaterialTheme.typography.labelMedium)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = if (state.isBalanceVisible) "KES ${state.balanceKes}" else "KES ••••••",
                        style = MaterialTheme.typography.displayMedium,
                    )
                    // TODO: verification badge based on state.verificationStatus
                }
            }
        }

        // Quick Action Buttons
        item {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                // TODO: Replace with QuickActionButton design-system component
                TextButton(onClick = { onAction(WalletDashboardAction.OnTopUpClick) }) { Text("Top Up") }
                TextButton(onClick = { onAction(WalletDashboardAction.OnSendMoneyClick) }) { Text("Send") }
                TextButton(onClick = { onAction(WalletDashboardAction.OnScanQrClick) }) { Text("Pay QR") }
            }
        }

        // Recent Transactions Header
        item {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Recent Transactions", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { onAction(WalletDashboardAction.OnSeeAllTransactionsClick) }) {
                    Text("See All")
                }
            }
        }

        // Transaction List
        items(
            items = state.recentTransactions,
            key = { it.id },
        ) { transaction ->
            TransactionListItem(
                transaction = transaction,
                onClick = { onAction(WalletDashboardAction.OnTransactionClick(transaction.id)) },
            )
        }

        item { Spacer(Modifier.height(24.dp)) }
    }
}

@Composable
private fun TransactionListItem(
    transaction: TransactionUi,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(transaction.counterpartyName, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = transaction.formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = transaction.amountKes,
            style = MaterialTheme.typography.bodyLarge,
            color = if (transaction.type == TransactionType.CREDIT)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error,
        )
    }
}

// Extension needed since Modifier.wrapContentSize() must be imported
private fun Modifier.wrapContentSize(): Modifier = this.then(
    Modifier.padding(0.dp)  // placeholder — replace with proper centering
)
