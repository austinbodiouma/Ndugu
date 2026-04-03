package com.example.ndugu.feature.marketplace.presentation.create

import com.example.ndugu.core.presentation.UiText

data class CreateListingState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val category: String? = null,
    val quantity: Int = 1,
    val isActive: Boolean = true,
    val photos: List<String> = emptyList(),
    val availableCategories: List<String> = listOf(
        "Textbooks & Books",
        "Electronics",
        "Dorm Essentials",
        "Event Tickets",
        "Other"
    )
)

sealed interface CreateListingAction {
    data object OnBackClick : CreateListingAction
    data class OnTitleChange(val title: String) : CreateListingAction
    data class OnPriceChange(val price: String) : CreateListingAction
    data class OnDescriptionChange(val description: String) : CreateListingAction
    data class OnCategoryChange(val category: String) : CreateListingAction
    data object OnQuantityIncrement : CreateListingAction
    data object OnQuantityDecrement : CreateListingAction
    data class OnActiveToggle(val isActive: Boolean) : CreateListingAction
    data object OnAddPhotoClick : CreateListingAction
    data object OnSaveDraftClick : CreateListingAction
    data object OnPublishClick : CreateListingAction
}

sealed interface CreateListingEvent {
    data object NavigateBack : CreateListingEvent
    data class ShowError(val message: UiText) : CreateListingEvent
    data object Success : CreateListingEvent
}
