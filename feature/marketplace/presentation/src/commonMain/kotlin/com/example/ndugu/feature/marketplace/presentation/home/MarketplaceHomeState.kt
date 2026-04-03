package com.example.ndugu.feature.marketplace.presentation.home

import com.example.ndugu.core.presentation.UiText

data class MarketplaceHomeState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val categories: List<String> = emptyList(),
    val selectedCategory: String = "All",
    val listings: List<MarketplaceListing> = emptyList(),
    val featuredListing: FeaturedListing? = null,
    val userAvatarUrl: String? = null
)

data class MarketplaceListing(
    val id: String,
    val title: String,
    val price: Double,
    val currency: String = "KES",
    val imageUrl: String,
    val sellerName: String,
    val sellerAvatarUrl: String?,
    val distance: String,
    val isVerified: Boolean = false,
    val isFavorite: Boolean = false
)

data class FeaturedListing(
    val id: String,
    val title: String,
    val subtitle: String,
    val tag: String,
    val imageUrl: String
)

sealed interface MarketplaceHomeAction {
    data object OnBackClick : MarketplaceHomeAction
    data object OnSearchClick : MarketplaceHomeAction
    data object OnSellClick : MarketplaceHomeAction
    data class OnCategoryClick(val category: String) : MarketplaceHomeAction
    data class OnListingClick(val listingId: String) : MarketplaceHomeAction
    data class OnToggleFavorite(val listingId: String) : MarketplaceHomeAction
}

sealed interface MarketplaceHomeEvent {
    data object NavigateBack : MarketplaceHomeEvent
    data object NavigateToSearch : MarketplaceHomeEvent
    data object NavigateToCreateListing : MarketplaceHomeEvent
    data class NavigateToListingDetail(val listingId: String) : MarketplaceHomeEvent
}
