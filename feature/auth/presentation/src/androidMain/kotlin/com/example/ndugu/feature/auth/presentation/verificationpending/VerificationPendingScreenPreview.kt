package com.example.ndugu.feature.auth.presentation.verificationpending

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showSystemUi = true)
@Composable
private fun VerificationPendingScreenPreview() {
    CampusWalletTheme {
        VerificationPendingScreen(
            state = VerificationPendingState(),
            onAction = {},
        )
    }
}
