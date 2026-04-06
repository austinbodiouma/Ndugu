package com.example.ndugu.feature.marketplace.presentation.myorders

import com.example.ndugu.core.presentation.UiText

data class MyOrdersState(
    val isLoading: Boolean = false,
    val selectedTab: OrderTab = OrderTab.ACTIVE,
    val activeCount: Int = 0,
    val inTransitCount: Int = 0,
    val pendingCount: Int = 0,
    val orders: List<OrderItem> = emptyList(),
    val error: UiText? = null,
)

data class OrderItem(
    val id: String,
    val title: String,
    val sellerName: String,
    val amount: Double,
    val status: OrderStatus,
    val imageUrl: String,
    val deliveryStatusText: String,
    val actionText: String,
    val actionType: OrderActionType,
)

enum class OrderStatus { IN_PROGRESS, UNDER_REVIEW, PICKUP, COMPLETED, DISPUTED }

enum class OrderTab { ACTIVE, COMPLETED, DISPUTED }

enum class OrderActionType { TRACK, DETAILS, MESSAGE_SELLER }

sealed interface MyOrdersAction {
    data object OnBackClick : MyOrdersAction
    data class OnTabSelected(val tab: OrderTab) : MyOrdersAction
    data class OnOrderClick(val orderId: String) : MyOrdersAction
    data class OnTrackClick(val orderId: String) : MyOrdersAction
    data class OnMessageSeller(val orderId: String) : MyOrdersAction
}

sealed interface MyOrdersEvent {
    data object NavigateBack : MyOrdersEvent
    data class NavigateToOrderDetail(val orderId: String) : MyOrdersEvent
    data class NavigateToTracking(val orderId: String) : MyOrdersEvent
    data class NavigateToChat(val sellerId: String) : MyOrdersEvent
}
