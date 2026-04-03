package com.example.ndugu.feature.payment.presentation.topup

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun TopUpPreview() {
    CampusWalletTheme {
        TopUpScreen(
            state = TopUpState(),
            onAction = {}
        )
    }
}
