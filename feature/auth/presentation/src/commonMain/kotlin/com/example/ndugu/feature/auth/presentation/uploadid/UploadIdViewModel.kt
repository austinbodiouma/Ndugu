package com.example.ndugu.feature.auth.presentation.uploadid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UploadIdViewModel : ViewModel() {

    private val _state = MutableStateFlow(UploadIdState())
    val state = _state.asStateFlow()

    private val _events = Channel<UploadIdEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: UploadIdAction) {
        when (action) {
            is UploadIdAction.OnImageSelected -> _state.update { it.copy(selectedImageBytes = action.bytes, error = null) }
            is UploadIdAction.OnPickImageClick -> { /* handled by platform: triggers image picker */ }
            is UploadIdAction.OnUploadClick -> upload()
            is UploadIdAction.OnSkipClick -> {
                viewModelScope.launch { _events.send(UploadIdEvent.NavigateToHome) }
            }
        }
    }

    private fun upload() {
        // TODO: inject and call UploadStudentIDUseCase
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _events.send(UploadIdEvent.NavigateToHome)
            _state.update { it.copy(isLoading = false) }
        }
    }
}
