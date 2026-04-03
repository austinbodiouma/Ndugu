package com.example.ndugu.feature.marketplace.presentation.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun CreateListingPreview() {
    CampusWalletTheme {
        CreateListingScreen(
            state = CreateListingState(),
            onAction = {}
        )
    }
}
