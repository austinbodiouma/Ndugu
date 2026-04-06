package com.example.ndugu.feature.payment.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object PaymentGraph

@Serializable
sealed interface PaymentRoute {
@Serializable
    data object QRScannerRoute : PaymentRoute

    @Serializable
    data object QRPaymentConfirmationRoute : PaymentRoute

    @Serializable
    data object TopUpRoute : PaymentRoute
}
