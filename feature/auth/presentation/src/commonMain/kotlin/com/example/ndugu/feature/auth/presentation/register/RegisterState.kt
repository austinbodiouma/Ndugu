package com.example.ndugu.feature.auth.presentation.register

import com.example.ndugu.core.presentation.UiText

data class RegisterState(
    val phone: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val phoneError: UiText? = null,
    val emailError: UiText? = null,
    val passwordError: UiText? = null,
)

sealed interface RegisterAction {
    data class OnPhoneChange(val phone: String) : RegisterAction
    data class OnEmailChange(val email: String) : RegisterAction
    data class OnPasswordChange(val password: String) : RegisterAction
    data class OnConfirmPasswordChange(val confirmPassword: String) : RegisterAction
    data object OnTogglePasswordVisibility : RegisterAction
    data object OnToggleConfirmPasswordVisibility : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnLoginClick : RegisterAction
}

sealed interface RegisterEvent {
    data class NavigateToOtpVerify(val phone: String) : RegisterEvent
    data object NavigateToLogin : RegisterEvent
    data class ShowSnackbar(val message: UiText) : RegisterEvent
}
