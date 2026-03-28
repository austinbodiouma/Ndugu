package com.example.ndugu.feature.auth.presentation.otp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpVerifyViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val phone: String = checkNotNull(savedStateHandle["phone"])

    private val _state = MutableStateFlow(OtpVerifyState(phone = phone))
    val state = _state.asStateFlow()

    private val _events = Channel<OtpVerifyEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: OtpVerifyAction) {
        when (action) {
            is OtpVerifyAction.OnOtpChange -> _state.update { it.copy(otpCode = action.code, otpError = null) }
            is OtpVerifyAction.OnVerifyClick -> verify()
            is OtpVerifyAction.OnResendClick -> resendOtp()
        }
    }

    private fun verify() {
        // TODO: inject and call VerifyOTPUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _events.send(OtpVerifyEvent.NavigateToUploadId)
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun resendOtp() {
        // TODO: call resend OTP API
    }
}
