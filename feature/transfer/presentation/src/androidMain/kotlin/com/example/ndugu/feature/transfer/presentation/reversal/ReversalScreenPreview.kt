package com.example.ndugu.feature.transfer.presentation.reversal

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Reversal — Confirm", showBackground = true)
@Composable
private fun ReversalScreenPreview() {
    MaterialTheme {
        ReversalScreen(
            state = ReversalState(
                transactionId = "tx-001",
                recipientName = "Jane Wambui",
                amountKes = "500",
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Reversal — Loading", showBackground = true)
@Composable
private fun ReversalScreenLoadingPreview() {
    MaterialTheme {
        ReversalScreen(
            state = ReversalState(
                transactionId = "tx-001",
                recipientName = "Jane Wambui",
                amountKes = "500",
                isLoading = true,
            ),
            onAction = {},
        )
    }
}
