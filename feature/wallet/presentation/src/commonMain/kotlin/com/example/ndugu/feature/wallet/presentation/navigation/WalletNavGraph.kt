package com.example.ndugu.feature.wallet.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.ndugu.feature.wallet.presentation.budget.BudgetRoot
import com.example.ndugu.feature.wallet.presentation.dashboard.WalletDashboardRoot
import com.example.ndugu.feature.wallet.presentation.history.TransactionHistoryRoot
import com.example.ndugu.feature.wallet.presentation.transactiondetail.TransactionDetailRoot

fun NavGraphBuilder.walletGraph(
    navController: NavController,
    onNavigateToSendMoney: () -> Unit,
    onNavigateToScanQr: () -> Unit,
    onNavigateToReversal: (String) -> Unit,
) {
    navigation<WalletDashboardRoute>(startDestination = WalletDashboardRoute) {
        composable<WalletDashboardRoute> {
            WalletDashboardRoot(
                onNavigateToTopUp = { /* top-up: M-Pesa STK Push — handled by platform */ },
                onNavigateToSendMoney = onNavigateToSendMoney,
                onNavigateToScanQr = onNavigateToScanQr,
                onNavigateToTransactionHistory = { navController.navigate(TransactionHistoryRoute) },
                onNavigateToTransactionDetail = { navController.navigate(TransactionDetailRoute(it)) },
            )
        }
        composable<TransactionHistoryRoute> {
            TransactionHistoryRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToDetail = { navController.navigate(TransactionDetailRoute(it)) },
            )
        }
        composable<TransactionDetailRoute> { backStackEntry ->
            val route: TransactionDetailRoute = backStackEntry.toRoute()
            TransactionDetailRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToReversal = onNavigateToReversal,
            )
        }
        composable<BudgetRoute> {
            BudgetRoot(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
