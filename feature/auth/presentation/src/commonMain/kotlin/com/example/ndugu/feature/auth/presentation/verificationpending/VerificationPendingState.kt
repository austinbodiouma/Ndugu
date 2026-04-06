package com.example.ndugu.feature.auth.presentation.verificationpending

import com.example.ndugu.core.presentation.UiText

data class VerificationPendingState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
)

sealed interface VerificationPendingAction {
    data object OnBackClick : VerificationPendingAction
    data object OnCheckStatusClick : VerificationPendingAction
    data object OnLogoutClick : VerificationPendingAction
}

sealed interface VerificationPendingEvent {
    data object NavigateBack : VerificationPendingEvent
    data object NavigateToHome : VerificationPendingEvent
}
