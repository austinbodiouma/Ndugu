package com.example.ndugu.feature.marketplace.presentation.di

import com.example.ndugu.feature.marketplace.presentation.home.MarketplaceHomeViewModel
import com.example.ndugu.feature.marketplace.presentation.detail.ListingDetailViewModel
import com.example.ndugu.feature.marketplace.presentation.create.CreateListingViewModel
import com.example.ndugu.feature.marketplace.presentation.seller.SellerDashboardViewModel
import com.example.ndugu.feature.marketplace.presentation.dispute.DisputeViewModel
import com.example.ndugu.feature.marketplace.presentation.myorders.MyOrdersViewModel
import com.example.ndugu.feature.marketplace.presentation.tracking.OrderTrackingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MarketplacePresentationModule = module {
    viewModelOf(::MarketplaceHomeViewModel)
    viewModelOf(::ListingDetailViewModel)
    viewModelOf(::CreateListingViewModel)
    viewModelOf(::SellerDashboardViewModel)
    viewModelOf(::DisputeViewModel)
    viewModelOf(::MyOrdersViewModel)
    viewModelOf(::OrderTrackingViewModel)
}
