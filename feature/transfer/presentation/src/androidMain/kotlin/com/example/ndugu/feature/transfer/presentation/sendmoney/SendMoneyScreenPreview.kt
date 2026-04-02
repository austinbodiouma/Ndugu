package com.example.ndugu.feature.transfer.presentation.sendmoney

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Send Money — Amount entry step", showBackground = true)
@Composable
private fun SendMoneyScreenAmountStepPreview() {
    CampusWalletTheme {
        SendMoneyScreen(
            state = SendMoneyState(
                recipientPhone = "+254712345678",
                recipientName = "Jane Wambui",
                availableBalanceKes = "4,380.00",
                isConfirmStep = false,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Send Money — Confirm step", showBackground = true)
@Composable
private fun SendMoneyScreenConfirmStepPreview() {
    CampusWalletTheme {
        SendMoneyScreen(
            state = SendMoneyState(
                recipientPhone = "+254712345678",
                recipientName = "Jane Wambui",
                amountKes = "500",
                memo = "Lunch split",
                availableBalanceKes = "4,380.00",
                isConfirmStep = true,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "Send Money — Confirm, no memo", showBackground = true)
@Composable
private fun SendMoneyScreenConfirmNoMemoPreview() {
    CampusWalletTheme {
        SendMoneyScreen(
            state = SendMoneyState(
                recipientPhone = "+254712345678",
                recipientName = "Brian Otieno",
                amountKes = "1,000",
                availableBalanceKes = "4,380.00",
                isConfirmStep = true,
            ),
            onAction = {},
        )
    }
}
