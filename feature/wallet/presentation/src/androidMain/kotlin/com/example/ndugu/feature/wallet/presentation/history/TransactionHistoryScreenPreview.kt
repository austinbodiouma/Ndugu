package com.example.ndugu.feature.wallet.presentation.history

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionType
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionUi

private val sampleTransactions = listOf(
    TransactionUi("1", TransactionType.CREDIT, "Brian Otieno", "+ KES 500", "Today, 10:32 AM", "Lunch money"),
    TransactionUi("2", TransactionType.DEBIT, "Mama Mboga Cafe", "- KES 120", "Today, 08:15 AM", null),
    TransactionUi("3", TransactionType.DEBIT, "Jane Wambui", "- KES 1,000", "Yesterday, 04:50 PM", "Rent contribution"),
    TransactionUi("4", TransactionType.CREDIT, "M-Pesa Top-Up", "+ KES 3,000", "26 Mar, 09:00 AM", null),
    TransactionUi("5", TransactionType.DEBIT, "Stationery Shop", "- KES 350", "25 Mar, 02:10 PM", "Notes and pens"),
)

@Preview(name = "Transaction History — Loaded", showBackground = true)
@Composable
private fun TransactionHistoryScreenPreview() {
    MaterialTheme {
        TransactionHistoryScreen(
            state = TransactionHistoryState(transactions = sampleTransactions),
            onAction = {},
        )
    }
}

@Preview(name = "Transaction History — Empty", showBackground = true)
@Composable
private fun TransactionHistoryScreenEmptyPreview() {
    MaterialTheme {
        TransactionHistoryScreen(
            state = TransactionHistoryState(transactions = emptyList()),
            onAction = {},
        )
    }
}
