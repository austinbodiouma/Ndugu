package com.example.ndugu.feature.auth.presentation.login

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Login — Empty", showBackground = true)
@Composable
private fun LoginScreenEmptyPreview() {
    CampusWalletTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {},
        )
    }
}

@Preview(name = "Login — Loading", showBackground = true)
@Composable
private fun LoginScreenLoadingPreview() {
    CampusWalletTheme {
        LoginScreen(
            state = LoginState(
                phone = "0712345678",
                isLoading = true,
            ),
            onAction = {},
        )
    }
}
