package com.example.ndugu.feature.wallet.presentation.profile

import com.example.ndugu.core.presentation.UiText

data class ProfileSettingsState(
    val isLoading: Boolean = false,
    val displayName: String = "",
    val email: String = "",
    val phone: String = "",
    val university: String = "",
    val classOf: String = "",
    val isVerified: Boolean = false,
    val avatarUrl: String? = null,
    val error: UiText? = null,
)

sealed interface ProfileSettingsAction {
    data object OnBackClick : ProfileSettingsAction
    data object OnNotificationsClick : ProfileSettingsAction
    data object OnPersonalInfoClick : ProfileSettingsAction
    data object OnBudgetsClick : ProfileSettingsAction
    data object OnTransactionHistoryClick : ProfileSettingsAction
    data object OnShopClick : ProfileSettingsAction
    data object OnYourOrdersClick : ProfileSettingsAction
    data object OnSecurityClick : ProfileSettingsAction
    data object OnSupportClick : ProfileSettingsAction
    data object OnLogoutClick : ProfileSettingsAction
}

sealed interface ProfileSettingsEvent {
    data object NavigateBack : ProfileSettingsEvent
    data object NavigateToLogin : ProfileSettingsEvent
}
