package com.example.ndugu.feature.marketplace.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.ndugu.feature.marketplace.presentation.home.MarketplaceHomeRoot
import com.example.ndugu.feature.marketplace.presentation.detail.ListingDetailRoot
import com.example.ndugu.feature.marketplace.presentation.create.CreateListingRoot
import com.example.ndugu.feature.marketplace.presentation.seller.SellerDashboardRoot
import com.example.ndugu.feature.marketplace.presentation.dispute.DisputeRoot

fun NavGraphBuilder.marketplaceGraph(
    navController: NavController,
) {
    navigation<MarketplaceRoute.MarketplaceHomeRoute>(
        startDestination = MarketplaceRoute.MarketplaceHomeRoute
    ) {
        composable<MarketplaceRoute.MarketplaceHomeRoute> {
            MarketplaceHomeRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSearch = { /* TODO: Implement Search */ },
                onNavigateToCreateListing = { navController.navigate(MarketplaceRoute.CreateListingRoute) },
                onNavigateToListingDetail = { listingId -> 
                    navController.navigate(MarketplaceRoute.ListingDetailRoute)
                }
            )
        }
        composable<MarketplaceRoute.ListingDetailRoute> {
            ListingDetailRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<MarketplaceRoute.CreateListingRoute> {
            CreateListingRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<MarketplaceRoute.SellerDashboardRoute> {
            SellerDashboardRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCreateListing = { navController.navigate(MarketplaceRoute.CreateListingRoute) },
                onNavigateToListingDetail = { listingId ->
                    navController.navigate(MarketplaceRoute.ListingDetailRoute)
                }
            )
        }
        composable<MarketplaceRoute.DisputeRoute> {
            DisputeRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
