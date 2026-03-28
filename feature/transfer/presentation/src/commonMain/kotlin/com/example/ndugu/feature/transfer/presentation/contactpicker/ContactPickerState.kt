package com.example.ndugu.feature.transfer.presentation.contactpicker

import com.example.ndugu.core.presentation.UiText

data class ContactPickerState(
    val searchQuery: String = "",
    val contacts: List<ContactUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
)

data class ContactUi(
    val phone: String,
    val displayName: String,
    val isOnCampusWallet: Boolean,
    val avatarUrl: String? = null,
)

sealed interface ContactPickerAction {
    data class OnSearchQueryChange(val query: String) : ContactPickerAction
    data class OnContactSelected(val contact: ContactUi) : ContactPickerAction
    data object OnManualEntryClick : ContactPickerAction
    data object OnBackClick : ContactPickerAction
}

sealed interface ContactPickerEvent {
    data class NavigateToAmountEntry(val phone: String, val recipientName: String) : ContactPickerEvent
    data object NavigateBack : ContactPickerEvent
    data class ShowSnackbar(val message: UiText) : ContactPickerEvent
}
