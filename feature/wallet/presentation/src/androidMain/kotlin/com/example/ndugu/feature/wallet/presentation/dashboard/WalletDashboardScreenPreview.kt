package com.example.ndugu.feature.wallet.presentation.dashboard

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

private val sampleTransactions = listOf(
    TransactionUi(
        id = "1",
        type = TransactionType.CREDIT,
        counterpartyName = "Brian Otieno",
        amountKes = "+ KES 500",
        formattedDate = "Today, 10:32 AM",
        memo = "Lunch money",
    ),
    TransactionUi(
        id = "2",
        type = TransactionType.DEBIT,
        counterpartyName = "Mama Mboga Cafe",
        amountKes = "- KES 120",
        formattedDate = "Today, 08:15 AM",
        memo = null,
    ),
    TransactionUi(
        id = "3",
        type = TransactionType.DEBIT,
        counterpartyName = "Jane Wambui",
        amountKes = "- KES 1,000",
        formattedDate = "Yesterday, 04:50 PM",
        memo = "Rent contribution",
    ),
    TransactionUi(
        id = "4",
        type = TransactionType.CREDIT,
        counterpartyName = "M-Pesa Top-Up",
        amountKes = "+ KES 3,000",
        formattedDate = "26 Mar, 09:00 AM",
        memo = null,
    ),
)

@Preview(name = "Wallet Dashboard — Loaded", showBackground = true)
@Composable
private fun WalletDashboardScreenPreview() {
    CampusWalletTheme {
        WalletDashboardScreen(
            state = WalletDashboardState(
                balanceKes = "4,380.00",
                isBalanceVisible = true,
                verificationStatus = VerificationStatus.VERIFIED,
                recentTransactions = sampleTransactions,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Wallet Dashboard — Balance hidden", showBackground = true)
@Composable
private fun WalletDashboardScreenHiddenPreview() {
    CampusWalletTheme {
        WalletDashboardScreen(
            state = WalletDashboardState(
                balanceKes = "4,380.00",
                isBalanceVisible = false,
                verificationStatus = VerificationStatus.PENDING,
                recentTransactions = sampleTransactions,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Wallet Dashboard — Empty", showBackground = true)
@Composable
private fun WalletDashboardScreenEmptyPreview() {
    CampusWalletTheme {
        WalletDashboardScreen(
            state = WalletDashboardState(
                balanceKes = "0.00",
                verificationStatus = VerificationStatus.PENDING,
                recentTransactions = emptyList(),
            ),
            onAction = {},
        )
    }
}
