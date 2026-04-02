package com.example.ndugu.feature.wallet.presentation.history

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionIconType
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionType
import com.example.ndugu.feature.wallet.presentation.dashboard.TransactionUi

private val sampleTransactions = listOf(
    TransactionUi(
        id = "1",
        type = TransactionType.CREDIT,
        iconType = TransactionIconType.PERSON,
        counterpartyName = "Brian Otieno",
        amountKes = "+ KES 500",
        formattedDate = "Today, 10:32 AM",
        memo = "Lunch money",
    ),
    TransactionUi(
        id = "2",
        type = TransactionType.DEBIT,
        iconType = TransactionIconType.RESTAURANT,
        counterpartyName = "Mama Mboga Cafe",
        amountKes = "- KES 120",
        formattedDate = "Today, 08:15 AM",
        memo = null,
    ),
    TransactionUi(
        id = "3",
        type = TransactionType.DEBIT,
        iconType = TransactionIconType.PERSON,
        counterpartyName = "Jane Wambui",
        amountKes = "- KES 1,000",
        formattedDate = "Yesterday, 04:50 PM",
        memo = "Rent contribution",
    ),
    TransactionUi(
        id = "4",
        type = TransactionType.CREDIT,
        iconType = TransactionIconType.BANK,
        counterpartyName = "M-Pesa Top-Up",
        amountKes = "+ KES 3,000",
        formattedDate = "26 Mar, 09:00 AM",
        memo = null,
    ),
    TransactionUi(
        id = "5",
        type = TransactionType.DEBIT,
        iconType = TransactionIconType.SHOPPING_BAG,
        counterpartyName = "Stationery Shop",
        amountKes = "- KES 350",
        formattedDate = "25 Mar, 02:10 PM",
        memo = "Notes and pens",
    ),
)

@Preview(name = "Transaction History — Loaded", showBackground = true)
@Composable
private fun TransactionHistoryScreenPreview() {
    CampusWalletTheme {
        TransactionHistoryScreen(
            state = TransactionHistoryState(transactions = sampleTransactions),
            onAction = {},
        )
    }
}

@Preview(name = "Transaction History — Empty", showBackground = true)
@Composable
private fun TransactionHistoryScreenEmptyPreview() {
    CampusWalletTheme {
        TransactionHistoryScreen(
            state = TransactionHistoryState(transactions = emptyList()),
            onAction = {},
        )
    }
}
