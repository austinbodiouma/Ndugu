package com.example.ndugu.feature.transfer.presentation.navigation

import ContactPickerRoot
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.ndugu.feature.transfer.presentation.reversal.ReversalRoot
import com.example.ndugu.feature.transfer.presentation.sendmoney.SendMoneyRoot

fun NavGraphBuilder.transferGraph(
    navController: NavController,
    onNavigateToTransactionDetail: (String) -> Unit,
) {
    navigation<TransferGraph>(startDestination = ContactPickerRoute) {
        composable<ContactPickerRoute> {
            ContactPickerRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAmountEntry = { phone, recipientName ->
                    navController.navigate(SendMoneyRoute(recipientPhone = phone, recipientName = recipientName))
                },
            )
        }
        composable<SendMoneyRoute> { backStackEntry ->
            val route: SendMoneyRoute = backStackEntry.toRoute()
            SendMoneyRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToSuccess = { transactionId ->
                    onNavigateToTransactionDetail(transactionId)
                },
            )
        }
        composable<TransferReversalRoute> { backStackEntry ->
            val route: TransferReversalRoute = backStackEntry.toRoute()
            ReversalRoot(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
