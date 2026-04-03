package com.example.ndugu.feature.marketplace.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateListingViewModel : ViewModel() {

    private val _state = MutableStateFlow(CreateListingState())
    val state = _state.asStateFlow()

    private val _events = Channel<CreateListingEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CreateListingAction) {
        when (action) {
            is CreateListingAction.OnBackClick -> {
                viewModelScope.launch { _events.send(CreateListingEvent.NavigateBack) }
            }
            is CreateListingAction.OnTitleChange -> {
                _state.update { it.copy(title = action.title) }
            }
            is CreateListingAction.OnPriceChange -> {
                _state.update { it.copy(price = action.price) }
            }
            is CreateListingAction.OnDescriptionChange -> {
                _state.update { it.copy(description = action.description) }
            }
            is CreateListingAction.OnCategoryChange -> {
                _state.update { it.copy(category = action.category) }
            }
            CreateListingAction.OnQuantityIncrement -> {
                _state.update { it.copy(quantity = it.quantity + 1) }
            }
            CreateListingAction.OnQuantityDecrement -> {
                if (_state.value.quantity > 1) {
                    _state.update { it.copy(quantity = it.quantity - 1) }
                }
            }
            is CreateListingAction.OnActiveToggle -> {
                _state.update { it.copy(isActive = action.isActive) }
            }
            CreateListingAction.OnAddPhotoClick -> {
                // TODO: Implement image picking logic
            }
            CreateListingAction.OnSaveDraftClick -> {
                // TODO: Save draft logic
            }
            CreateListingAction.OnPublishClick -> {
                // TODO: Publish listing logic
            }
        }
    }
}
