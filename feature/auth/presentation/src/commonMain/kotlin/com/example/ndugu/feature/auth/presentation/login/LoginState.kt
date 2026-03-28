package com.example.ndugu.feature.auth.presentation.login

import com.example.ndugu.core.presentation.UiText

data class LoginState(
    val phone: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val phoneError: UiText? = null,
    val passwordError: UiText? = null,
)

sealed interface LoginAction {
    data class OnPhoneChange(val phone: String) : LoginAction
    data class OnPasswordChange(val password: String) : LoginAction
    data object OnTogglePasswordVisibility : LoginAction
    data object OnLoginClick : LoginAction
    data object OnRegisterClick : LoginAction
    data object OnBiometricLoginClick : LoginAction
}

sealed interface LoginEvent {
    data object NavigateToHome : LoginEvent
    data object NavigateToRegister : LoginEvent
    data class ShowSnackbar(val message: UiText) : LoginEvent
}
