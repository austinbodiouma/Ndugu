package com.example.ndugu.feature.marketplace.presentation.dispute

import com.example.ndugu.core.presentation.UiText

data class DisputeState(
    val isLoading: Boolean = false,
    val error: UiText? = null
)

sealed interface DisputeAction {
    data object OnBackClick : DisputeAction
}

sealed interface DisputeEvent {
    data object NavigateBack : DisputeEvent
}
