package com.example.ndugu.feature.payment.presentation.topup

import com.example.ndugu.core.presentation.UiText

data class TopUpState(
    val isLoading: Boolean = false,
    val error: UiText? = null
)

sealed interface TopUpAction {
    data object OnBackClick : TopUpAction
}

sealed interface TopUpEvent {
    data object NavigateBack : TopUpEvent
}
