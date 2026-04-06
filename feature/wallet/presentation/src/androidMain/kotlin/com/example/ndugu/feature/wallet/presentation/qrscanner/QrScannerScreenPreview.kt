package com.example.ndugu.feature.wallet.presentation.qrscanner

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme


@Preview(showBackground = true)
@Composable
private fun QrScannerScreenPreview() {
    CampusWalletTheme {
        QrScannerScreen(
            state = QrScannerState(),
            onAction = {}
        )
    }
}
