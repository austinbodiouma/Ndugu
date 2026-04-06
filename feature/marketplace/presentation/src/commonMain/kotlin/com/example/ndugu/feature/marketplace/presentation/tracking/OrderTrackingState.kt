package com.example.ndugu.feature.marketplace.presentation.tracking

import com.example.ndugu.core.presentation.UiText

data class OrderTrackingState(
    val isLoading: Boolean = false,
    val orderId: String = "",
    val status: OrderStatus = OrderStatus.SELLER_PREPARING,
    val statusDescription: String = "",
    val item: TrackedItem? = null,
    val timeline: List<TimelineStep> = emptyList(),
    val error: UiText? = null,
)

data class TrackedItem(
    val id: String,
    val title: String,
    val sellerName: String,
    val price: Double,
    val date: String,
    val imageUrl: String?,
)

data class TimelineStep(
    val title: String,
    val description: String,
    val status: StepStatus,
    val icon: String,
)

enum class OrderStatus {
    PAYMENT_RECEIVED,
    SELLER_PREPARING,
    SELLER_CONFIRMED,
    ORDER_COMPLETED,
}

enum class StepStatus {
    COMPLETED,
    ACTIVE,
    UPCOMING,
}

sealed interface OrderTrackingAction {
    data object OnBackClick : OrderTrackingAction
    data object OnConfirmReceipt : OrderTrackingAction
    data object OnRaiseDispute : OrderTrackingAction
    data object OnMessageSeller : OrderTrackingAction
}

sealed interface OrderTrackingEvent {
    data object NavigateBack : OrderTrackingEvent
    data class NavigateToChat(val sellerId: String) : OrderTrackingEvent
}
