package com.example.ndugu.feature.transfer.presentation.contactpicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactPickerViewModel : ViewModel() {

    private val _state = MutableStateFlow(ContactPickerState())
    val state = _state.asStateFlow()

    private val _events = Channel<ContactPickerEvent>()
    val events = _events.receiveAsFlow()

    init { loadContacts() }

    fun onAction(action: ContactPickerAction) {
        when (action) {
            is ContactPickerAction.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = action.query) }
                // TODO: filter contacts by query
            }
            is ContactPickerAction.OnContactSelected -> {
                viewModelScope.launch {
                    _events.send(
                        ContactPickerEvent.NavigateToAmountEntry(
                            phone = action.contact.phone,
                            recipientName = action.contact.displayName,
                        )
                    )
                }
            }
            is ContactPickerAction.OnManualEntryClick -> {
                // Navigate to amount with empty phone — user types it
                viewModelScope.launch {
                    _events.send(ContactPickerEvent.NavigateToAmountEntry(phone = "", recipientName = ""))
                }
            }
            is ContactPickerAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ContactPickerEvent.NavigateBack) }
            }
        }
    }

    private fun loadContacts() {
        // TODO: inject and call contacts sync use case / platform contacts API via interface
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
