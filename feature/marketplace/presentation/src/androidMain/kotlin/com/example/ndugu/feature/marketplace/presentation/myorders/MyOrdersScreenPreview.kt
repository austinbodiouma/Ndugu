package com.example.ndugu.feature.marketplace.presentation.myorders

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme

@Preview(showSystemUi = true)
@Composable
private fun MyOrdersScreenPreview() {
    CampusWalletTheme {
        MyOrdersScreen(
            state = MyOrdersState(
                activeCount = 4,
                inTransitCount = 2,
                pendingCount = 2,
                orders = listOf(
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
                    )
                )
            ),
            onAction = {}
        )
    }
}
