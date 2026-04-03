package com.example.ndugu.feature.marketplace.presentation.detail

import com.example.ndugu.core.presentation.UiText

data class ListingDetailState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val listing: ListingDetail? = null
)

data class ListingDetail(
    val id: String,
    val title: String,
    val price: Double,
    val originalPrice: Double? = null,
    val currency: String = "KES",
    val category: String,
    val rating: Float,
    val reviewCount: Int,
    val images: List<String>,
    val seller: SellerInfo,
    val description: String,
    val features: List<String>,
    val deliveryInfo: String,
    val returnPolicy: String,
    val securityInfo: String,
    val reviews: List<Review>
)

data class SellerInfo(
    val name: String,
    val avatarUrl: String?,
    val isVerified: Boolean,
    val role: String
)

data class Review(
    val id: String,
    val userName: String,
    val userInitials: String,
    val rating: Int,
    val comment: String,
    val date: String,
    val avatarColor: Long
)

sealed interface ListingDetailAction {
    data object OnBackClick : ListingDetailAction
    data object OnShareClick : ListingDetailAction
    data object OnFavoriteClick : ListingDetailAction
    data object OnMessageSellerClick : ListingDetailAction
    data object OnAddToCartClick : ListingDetailAction
    data object OnBuyNowClick : ListingDetailAction
}

sealed interface ListingDetailEvent {
    data object NavigateBack : ListingDetailEvent
    data class ShowError(val error: UiText) : ListingDetailEvent
}

