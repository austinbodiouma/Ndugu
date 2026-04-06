package com.example.ndugu.feature.marketplace.presentation.myorders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyOrdersViewModel : ViewModel() {

    private val _state = MutableStateFlow(MyOrdersState())
    val state = _state.asStateFlow()

    private val _events = Channel<MyOrdersEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadMockOrders()
    }

    private fun loadMockOrders() {
        _state.update {
            it.copy(
                activeCount = 4,
                inTransitCount = 2,
                pendingCount = 2,
                orders = getMockActiveOrders()
            )
        }
    }

    fun onAction(action: MyOrdersAction) {
        when (action) {
            is MyOrdersAction.OnBackClick -> {
                viewModelScope.launch { _events.send(MyOrdersEvent.NavigateBack) }
            }
            is MyOrdersAction.OnTabSelected -> {
                _state.update { 
                    it.copy(
                        selectedTab = action.tab,
                        orders = when(action.tab) {
                            OrderTab.ACTIVE -> getMockActiveOrders()
                            OrderTab.COMPLETED -> getMockCompletedOrders()
                            OrderTab.DISPUTED -> getMockDisputedOrders()
                        }
                    )
                }
            }
            is MyOrdersAction.OnOrderClick -> {
                viewModelScope.launch {
                    _events.send(MyOrdersEvent.NavigateToOrderDetail(action.orderId))
                }
            }
            is MyOrdersAction.OnTrackClick -> {
                viewModelScope.launch {
                    _events.send(MyOrdersEvent.NavigateToTracking(action.orderId))
                }
            }
            is MyOrdersAction.OnMessageSeller -> {
                viewModelScope.launch {
                    _events.send(MyOrdersEvent.NavigateToChat(action.orderId)) // Using orderId as sellerId for mock
                }
            }
        }
    }

    private fun getMockActiveOrders() = listOf(
        OrderItem(
            id = "1",
            title = "Apple MacBook Pro M1",
            sellerName = "Alex Chen",
            amount = 850.00,
            status = OrderStatus.IN_PROGRESS,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAr_XuPWFPXNDdiP8aUSumwcj2kCD6bt3VWsTDvn23RUdNGfs6hI6sxZwoz5ybS_xv795mFmTJ5iHDvZFbZdF1AWDYRQXsSuP-Hzc8UZk4zR2VJkQmB3iuQLYoGJ5LgxkEUn283N4I0fo4tlNJ3cqb3v1x3uCXqOzO4Ovwx09yMcavQmdJwvIiNe_98f6o2hojO0RznZGGJYn9BZdrTdHMV89mn10UDUmj1iOgIXKbjD_gbvhf-pSj-rxr5f79SpRTdhMUv__9PUZo",
            deliveryStatusText = "Estimated delivery: Tomorrow",
            actionText = "Track",
            actionType = OrderActionType.TRACK
        ),
        OrderItem(
            id = "2",
            title = "Advanced Calculus Bundle",
            sellerName = "Sarah Miller",
            amount = 45.00,
            status = OrderStatus.UNDER_REVIEW,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCqxgQrykwluBPwv0_uToIYf2aEEU757wW-sJDkLZbD81jUFGlz3ydH66UcAgPg-ZuOYhjuir1o-s9pUtLnn4UvIfxOQXLoYRe1KP0kG6br5s76osGWr7sUya142KqdqtEskpJ4AOJykiWkqAW72aJj_A7XOn_yzbPVn_n4VW7dg-si4VYctCyo76I84pmCpVCC2pV3JgoC7uPSennmZV4GMaXPhLmJaUHuNhjqr_-V8NQDUKoy_5C2CqlTnaFGLjbBR1uEFu_TmcM",
            deliveryStatusText = "Verifying payment status",
            actionText = "Details",
            actionType = OrderActionType.DETAILS
        ),
        OrderItem(
            id = "3",
            title = "Cruiser Bicycle - Teal",
            sellerName = "Jordan Lee",
            amount = 120.00,
            status = OrderStatus.IN_PROGRESS,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCU/Fr5BZKAWvzoho9sC38NuQyOq-TVtvNUmreKsAhKDrOLTLhydLlelMH8hKyL-2KuoHZnGT2mwmf4pn1i59I58h7K4vdGaO8D2kH8hfyl-DH-w5Z5DC5acTWEyb_y3xo79MKILk1yLXUAprCUWnZIPgShhRBP1-vFPsVDiCWDYo23CmHz6u0NUAwa3rA6gS_fYM1CD-klJnSjTsiz9CRMXrUI1TiaE9Qsy2rpY3EisHFHkSZy7bjuJs8tlC1oNw4II50B0d8KDyM",
            deliveryStatusText = "Pickup: Student Union Plaza",
            actionText = "Message Seller",
            actionType = OrderActionType.MESSAGE_SELLER
        )
    )

    private fun getMockCompletedOrders() = emptyList<OrderItem>()
    private fun getMockDisputedOrders() = emptyList<OrderItem>()
}
