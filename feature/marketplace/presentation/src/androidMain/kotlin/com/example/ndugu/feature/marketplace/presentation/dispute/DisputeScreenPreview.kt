package com.example.ndugu.feature.marketplace.presentation.dispute

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun DisputePreview() {
    CampusWalletTheme {
        DisputeScreen(
            state = DisputeState(),
            onAction = {}
        )
    }
}
