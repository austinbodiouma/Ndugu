package com.example.ndugu.feature.marketplace.presentation.seller

import com.example.ndugu.core.presentation.UiText
import com.example.ndugu.feature.marketplace.presentation.home.MarketplaceListing

data class SellerDashboardState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val sellerName: String = "",
    val sellerAvatarUrl: String? = null,
    val totalRevenue: Double = 0.0,
    val pendingOrdersCount: Int = 0,
    val activeListingsCount: Int = 0,
    val myListings: List<MarketplaceListing> = emptyList()
)

sealed interface SellerDashboardAction {
    data object OnBackClick : SellerDashboardAction
    data object OnRefresh : SellerDashboardAction
    data object OnCreateListingClick : SellerDashboardAction
    data class OnListingClick(val id: String) : SellerDashboardAction
}

sealed interface SellerDashboardEvent {
    data object NavigateBack : SellerDashboardEvent
    data object NavigateToCreateListing : SellerDashboardEvent
    data class NavigateToListingDetail(val id: String) : SellerDashboardEvent
}
