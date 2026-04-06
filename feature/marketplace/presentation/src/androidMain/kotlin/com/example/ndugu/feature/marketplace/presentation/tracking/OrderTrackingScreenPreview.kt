package com.example.ndugu.feature.marketplace.presentation.tracking

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showBackground = true)
@Composable
fun OrderTrackingScreenPreview() {
    CampusWalletTheme {
        OrderTrackingScreen(
            state = OrderTrackingState(
                orderId = "8472",
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
            ),
            onAction = {}
        )
    }
}
