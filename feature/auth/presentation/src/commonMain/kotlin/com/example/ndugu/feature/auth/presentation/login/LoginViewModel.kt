package com.example.ndugu.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnPhoneChange -> _state.update { it.copy(phone = action.phone, phoneError = null) }
            is LoginAction.OnPasswordChange -> _state.update { it.copy(password = action.password, passwordError = null) }
            is LoginAction.OnTogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is LoginAction.OnLoginClick -> login()
            is LoginAction.OnBiometricLoginClick -> biometricLogin()
            is LoginAction.OnRegisterClick -> {
                viewModelScope.launch { _events.send(LoginEvent.NavigateToRegister) }
            }
        }
    }

    private fun login() {
        // TODO: inject and call LoginUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _events.send(LoginEvent.NavigateToHome)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun biometricLogin() {
        // TODO: trigger platform-specific BiometricPrompt via interface
    }
}
