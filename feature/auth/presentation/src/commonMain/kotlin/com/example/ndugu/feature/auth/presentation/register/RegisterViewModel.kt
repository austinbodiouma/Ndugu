package com.example.ndugu.feature.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnPhoneChange -> _state.update { it.copy(phone = action.phone, phoneError = null) }
            is RegisterAction.OnEmailChange -> _state.update { it.copy(email = action.email, emailError = null) }
            is RegisterAction.OnPasswordChange -> _state.update { it.copy(password = action.password, passwordError = null) }
            is RegisterAction.OnConfirmPasswordChange -> _state.update { it.copy(confirmPassword = action.confirmPassword) }
            is RegisterAction.OnTogglePasswordVisibility -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            is RegisterAction.OnToggleConfirmPasswordVisibility -> _state.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
            is RegisterAction.OnRegisterClick -> register()
            is RegisterAction.OnLoginClick -> {
                viewModelScope.launch { _events.send(RegisterEvent.NavigateToLogin) }
            }
        }
    }

    private fun register() {
        // TODO: inject and call RegisterStudentUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            // Stub: navigate to OTP
            _events.send(RegisterEvent.NavigateToOtpVerify(phone = _state.value.phone))
            _state.update { it.copy(isLoading = false) }
        }
    }
}
