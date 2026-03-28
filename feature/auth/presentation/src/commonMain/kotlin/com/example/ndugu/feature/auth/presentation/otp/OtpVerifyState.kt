package com.example.ndugu.feature.auth.presentation.otp

import com.example.ndugu.core.presentation.UiText

data class OtpVerifyState(
    val phone: String = "",
    val otpCode: String = "",
    val isLoading: Boolean = false,
    val secondsUntilResend: Int = 60,
    val canResend: Boolean = false,
    val otpError: UiText? = null,
    val currentStep: Int = 2,
    val totalSteps: Int = 5,
)

sealed interface OtpVerifyAction {
    data class OnOtpChange(val code: String) : OtpVerifyAction
    data object OnVerifyClick : OtpVerifyAction
    data object OnResendClick : OtpVerifyAction
}

sealed interface OtpVerifyEvent {
    data object NavigateToUploadId : OtpVerifyEvent
    data class ShowSnackbar(val message: UiText) : OtpVerifyEvent
}
