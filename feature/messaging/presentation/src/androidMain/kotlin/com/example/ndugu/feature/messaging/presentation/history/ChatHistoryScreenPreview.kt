package com.example.ndugu.feature.messaging.presentation.history

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
private fun ChatHistoryPreview() {
    CampusWalletTheme {
        ChatHistoryScreen(
            state = ChatHistoryState(),
            onAction = {}
        )
    }
}
