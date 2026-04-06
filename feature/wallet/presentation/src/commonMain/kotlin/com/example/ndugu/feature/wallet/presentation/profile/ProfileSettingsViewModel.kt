package com.example.ndugu.feature.wallet.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileSettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileSettingsState())
    val state = _state.asStateFlow()

    private val _events = Channel<ProfileSettingsEvent>()
    val events = _events.receiveAsFlow()

    init {
        // Initial mock data from HTML mockup
        _state.update { it.copy(
            displayName = "Marcus Thorne",
            email = "m.thorne@stanford.edu",
            phone = "+1 (555) 012-3456",
            university = "Stanford University",
            classOf = "2025",
            isVerified = true,
            avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCC19DX9NcbRFELYRQO0Kogve-ZBaK4d0p-mcgdhPAGImF6ssYwH8JrysPDp1vORQEACiyR3zwbZAPXJ9zceRnV8Aoqy_9CCknCG2Ov2GT67sHCXTHgSMunQDVhOfPt7yzbNknANiudJoa21SeCPZALKZ1LO0Juk39mgdpmrztFW1kIkoYxcBe7EzvLlHsMj-bjK2r6X6WnKrYmkaNXBFID1uWddk3TuOcPN4Ko8poyWISu3smmD_lAkrcRDOjP-bAJqsJ53uJ6ew8"
        ) }
    }

    fun onAction(action: ProfileSettingsAction) {
        when (action) {
            is ProfileSettingsAction.OnBackClick -> {
                viewModelScope.launch { _events.send(ProfileSettingsEvent.NavigateBack) }
            }
            is ProfileSettingsAction.OnLogoutClick -> {
                viewModelScope.launch { _events.send(ProfileSettingsEvent.NavigateToLogin) }
            }
            else -> { /* TODO: Implement navigation for other actions */ }
        }
    }
}
