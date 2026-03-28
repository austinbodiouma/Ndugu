package com.example.ndugu.feature.auth.presentation.otp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(name = "OTP — Waiting for code", showBackground = true)
@Composable
private fun OtpVerifyScreenPreview() {
    CampusWalletTheme {
        OtpVerifyScreen(
            state = OtpVerifyState(
                phone = "+254 712 345 678",
                otpCode = "",
                secondsUntilResend = 45,
                canResend = false,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "OTP — Code entered", showBackground = true)
@Composable
private fun OtpVerifyScreenCodeEnteredPreview() {
    CampusWalletTheme {
        OtpVerifyScreen(
            state = OtpVerifyState(
                phone = "+254 712 345 678",
                otpCode = "123456",
                canResend = false,
            ),
            onAction = {},
        )
    }
}

@Preview(name = "OTP — Can resend", showBackground = true)
@Composable
private fun OtpVerifyScreenResendPreview() {
    CampusWalletTheme {
        OtpVerifyScreen(
            state = OtpVerifyState(
                phone = "+254 712 345 678",
                canResend = true,
                secondsUntilResend = 0,
            ),
            onAction = {},
        )
    }
}
