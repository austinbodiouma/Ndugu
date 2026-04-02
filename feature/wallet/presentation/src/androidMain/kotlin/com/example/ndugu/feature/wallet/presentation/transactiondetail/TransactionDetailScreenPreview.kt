package com.example.ndugu.feature.wallet.presentation.transactiondetail

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionType

@Preview(name = "Transaction Detail — Debit, reversible", showBackground = true)
@Composable
private fun TransactionDetailDebitPreview() {
    CampusWalletTheme {
        TransactionDetailScreen(
            state = TransactionDetailState(
                transactionId = "tx-001",
                type = TransactionType.DEBIT,
                amountKes = "- KES 500",
                counterpartyName = "Jane Wambui",
                counterpartyPhone = "+254712345678",
                formattedDate = "28 Mar 2026, 10:32 AM",
                memo = "Lunch split",
                canRequestReversal = true,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Transaction Detail — Credit, not reversible", showBackground = true)
@Composable
private fun TransactionDetailCreditPreview() {
    CampusWalletTheme {
        TransactionDetailScreen(
            state = TransactionDetailState(
                transactionId = "tx-002",
                type = TransactionType.CREDIT,
                amountKes = "+ KES 3,000",
                counterpartyName = "M-Pesa Top-Up",
                counterpartyPhone = "",
                formattedDate = "26 Mar 2026, 09:00 AM",
                memo = null,
                canRequestReversal = false,
            ),
            onAction = {},
        )
    }
}
