package com.example.ndugu.feature.payment.presentation.confirmation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun QRPaymentConfirmationPreview() {
    CampusWalletTheme {
        QRPaymentConfirmationScreen(
            state = QRPaymentConfirmationState(),
            onAction = {}
        )
    }
}
