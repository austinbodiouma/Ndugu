package com.example.ndugu.feature.marketplace.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MarketplaceRoute {
@Serializable
    data object MarketplaceHomeRoute : MarketplaceRoute

    @Serializable
    data object ListingDetailRoute : MarketplaceRoute

    @Serializable
    data object CreateListingRoute : MarketplaceRoute

    @Serializable
    data object SellerDashboardRoute : MarketplaceRoute

    @Serializable
    data object DisputeRoute : MarketplaceRoute

    @Serializable
    data object MyOrdersRoute : MarketplaceRoute

    @Serializable
    data class OrderTrackingRoute(val orderId: String) : MarketplaceRoute
}
