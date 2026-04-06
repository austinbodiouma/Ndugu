package com.example.ndugu.feature.marketplace.presentation.tracking

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.ndugu.feature.marketplace.presentation.navigation.MarketplaceRoute
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrderTrackingViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<MarketplaceRoute.OrderTrackingRoute>()
    private val orderId = route.orderId

    private val _state = MutableStateFlow(OrderTrackingState(orderId = orderId))
    val state = _state
        .onStart { loadOrderDetails() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            OrderTrackingState(orderId = orderId)
        )

    private val _events = Channel<OrderTrackingEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: OrderTrackingAction) {
        when (action) {
            OrderTrackingAction.OnBackClick -> {
                viewModelScope.launch { _events.send(OrderTrackingEvent.NavigateBack) }
            }
            OrderTrackingAction.OnConfirmReceipt -> {
                // Handle confirm receipt
            }
            OrderTrackingAction.OnMessageSeller -> {
                state.value.item?.let {
                    viewModelScope.launch {
                        _events.send(OrderTrackingEvent.NavigateToChat(it.id)) // Assuming item ID or seller ID
                    }
                }
            }
            OrderTrackingAction.OnRaiseDispute -> {
                // Handle raise dispute
            }
        }
    }

    private fun loadOrderDetails() {
        _state.update { it.copy(isLoading = true) }
        
        // Mocking data based on HTML design
        viewModelScope.launch {
            // Simulate network delay
            // delay(1000)
            
            _state.update {
                it.copy(
                    isLoading = false,
                    status = OrderStatus.SELLER_PREPARING,
                    statusDescription = "Waiting for seller to confirm delivery. Your funds are securely held in escrow.",
                    item = TrackedItem(
                        id = "item_1",
                        title = "MacBook Air M2 (2022)",
                        sellerName = "Alex Rivera",
                        price = 850.00,
                        date = "Oct 24, 2023",
                        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDtH42PKjLuCpRsuIgckKtLI1216NFwOcxvdoAxxsCSHuvc9nTiTcmmQEjAizH_YSXJU35PJqnAzt31ROq4-ozsySpuxszjz3H9-ynyJX2m-DfVah0OV3q3TvZQAo6V2QR7o2xPL0RiKBp582mb1VgNSahjDolrNXtknmoYeVEoCLgNwKEwNg7AuhzMFZtQqkhtHdzaTrm1v0y4qBWRQs4k4P-Jckpem5kryFPlvrPnX7vpyQixRR5V9uuYcUCPYdGHE-XgdT5_QN4"
                    ),
                    timeline = listOf(
                        TimelineStep(
                            title = "Payment Received",
                            description = "Payment received and held in escrow.",
                            status = StepStatus.COMPLETED,
                            icon = "check"
                        ),
                        TimelineStep(
                            title = "Seller Preparing",
                            description = "Seller is preparing your item for delivery.",
                            status = StepStatus.ACTIVE,
                            icon = "inventory_2"
                        ),
                        TimelineStep(
                            title = "Seller Confirmed",
                            description = "Seller has confirmed the item is ready.",
                            status = StepStatus.UPCOMING,
                            icon = "local_shipping"
                        ),
                        TimelineStep(
                            title = "Order Completed",
                            description = "Item received and funds released.",
                            status = StepStatus.UPCOMING,
                            icon = "verified"
                        )
                    )
                )
            }
        }
    }
}
