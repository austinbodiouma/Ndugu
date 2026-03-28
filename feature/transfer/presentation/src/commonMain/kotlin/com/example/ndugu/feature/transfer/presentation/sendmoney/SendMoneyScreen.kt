package com.example.ndugu.feature.transfer.presentation.sendmoney

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.HorizontalDivider
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun SendMoneyRoot(
    onNavigateBack: () -> Unit,
    onNavigateToSuccess: (String) -> Unit,
    viewModel: SendMoneyViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SendMoneyEvent.NavigateBack -> onNavigateBack()
            is SendMoneyEvent.NavigateToSuccess -> onNavigateToSuccess(event.transactionId)
            is SendMoneyEvent.ShowSnackbar -> { /* TODO: show snackbar */ }
        }
    }

    SendMoneyScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(
    state: SendMoneyState,
    onAction: (SendMoneyAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isConfirmStep) "Confirm Send" else "Send Money") },
                navigationIcon = {
                    IconButton(onClick = { onAction(SendMoneyAction.OnBackClick) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(Modifier.height(8.dp))

            // Recipient info
            Column {
                Text("To", style = MaterialTheme.typography.labelMedium)
                Text(state.recipientName.ifBlank { state.recipientPhone },
                    style = MaterialTheme.typography.titleMedium)
                if (state.recipientName.isNotBlank()) {
                    Text(state.recipientPhone, style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            HorizontalDivider()

            if (state.isConfirmStep) {
                // Confirm step — show summary
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ConfirmRow("Amount", "KES ${state.amountKes}")
                    if (state.memo.isNotBlank()) ConfirmRow("Note", state.memo)
                    ConfirmRow("From balance", "KES ${state.availableBalanceKes}")
                }

                Spacer(Modifier.weight(1f))

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    Button(
                        onClick = { onAction(SendMoneyAction.OnConfirmSendClick) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Send KES ${state.amountKes}")
                    }
                    OutlinedButton(
                        onClick = { onAction(SendMoneyAction.OnEditClick) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Edit")
                    }
                }
            } else {
                // Amount entry step
                // TODO: replace with CWTextField for amount input and memo field

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { onAction(SendMoneyAction.OnReviewClick) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.amountKes.isNotBlank(),
                ) {
                    Text("Review")
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ConfirmRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
