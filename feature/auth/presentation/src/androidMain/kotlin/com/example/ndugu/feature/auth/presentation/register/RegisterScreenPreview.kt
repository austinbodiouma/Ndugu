package com.example.ndugu.feature.auth.presentation.register

import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Register — Empty", showBackground = true)
@Composable
private fun RegisterScreenEmptyPreview() {
    CampusWalletTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {},
        )
    }
}

@Preview(name = "Register — Loading", showBackground = true)
@Composable
private fun RegisterScreenLoadingPreview() {
    CampusWalletTheme {
        RegisterScreen(
            state = RegisterState(
                phone = "0712345678",
                email = "alice@uni.ac.ke",
                isLoading = true,
            ),
            onAction = {},
        )
    }
}
