package com.example.ndugu.feature.payment.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ndugu.feature.payment.presentation.scanner.QRScannerRoot
import com.example.ndugu.feature.payment.presentation.confirmation.QRPaymentConfirmationRoot
import com.example.ndugu.feature.payment.presentation.topup.TopUpRoot

fun NavGraphBuilder.paymentGraph(
    navController: NavController,
) {
    navigation<PaymentGraph>(startDestination = PaymentRoute.QRScannerRoute) {
        composable<PaymentRoute.QRScannerRoute> {
            QRScannerRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<PaymentRoute.QRPaymentConfirmationRoute> {
            QRPaymentConfirmationRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable<PaymentRoute.TopUpRoute> {
            TopUpRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
