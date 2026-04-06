package com.example.ndugu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ndugu.core.designsystem.theme.CampusWalletTheme
import com.example.ndugu.feature.auth.presentation.navigation.AuthGraph
import com.example.ndugu.feature.auth.presentation.navigation.LoginRoute
import com.example.ndugu.feature.auth.presentation.navigation.SplashRoute
import com.example.ndugu.feature.auth.presentation.navigation.authGraph
import com.example.ndugu.feature.marketplace.presentation.navigation.MarketplaceGraph
import com.example.ndugu.feature.marketplace.presentation.navigation.MarketplaceRoute
import com.example.ndugu.feature.marketplace.presentation.navigation.marketplaceGraph
import com.example.ndugu.feature.messaging.presentation.navigation.MessagingGraph
import com.example.ndugu.feature.messaging.presentation.navigation.MessagingRoute
import com.example.ndugu.feature.messaging.presentation.navigation.messagingGraph
import com.example.ndugu.feature.payment.presentation.navigation.PaymentGraph
import com.example.ndugu.feature.payment.presentation.navigation.paymentGraph
import com.example.ndugu.feature.transfer.presentation.navigation.TransferGraph
import com.example.ndugu.feature.transfer.presentation.navigation.transferGraph
import com.example.ndugu.feature.wallet.presentation.navigation.*

data class BottomNavItem(
    val label: String,
    val icon: @Composable () -> Unit,
    val route: Any,
    val isFab: Boolean = false
)

@Composable
fun App() {
    CampusWalletTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val bottomNavItems = listOf(
            BottomNavItem("Home", { Icon(Icons.Outlined.Home, null) }, WalletDashboardRoute),
            BottomNavItem("Market", { Icon(Icons.Outlined.Storefront, null) }, MarketplaceRoute.MarketplaceHomeRoute),
            BottomNavItem("Scan", {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.QrCodeScanner, null, tint = MaterialTheme.colorScheme.onPrimary)
                }
            }, QrScannerRoute, isFab = true),
            BottomNavItem("Inbox", { Icon(Icons.Default.Chat, null) }, MessagingRoute.ChatHistoryRoute),
            BottomNavItem("Profile", { Icon(Icons.Outlined.Person, null) }, ProfileSettingsRoute)
        )

        val shouldShowBottomBar = bottomNavItems.any { item ->
            currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
        }

        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp
                    ) {
                        bottomNavItems.forEach { item ->
                            val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
                            
                            NavigationBarItem(
                                icon = item.icon,
                                label = if (!item.isFab) { { Text(item.label) } } else null,
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = AuthGraph,
                modifier = Modifier.padding(padding)
            ) {
                authGraph(
                    navController = navController,
                    onNavigateToHome = {
                        navController.navigate(WalletGraph) {
                            popUpTo(AuthGraph) { inclusive = true }
                        }
                    },
                    onSkipAuth = {
                        navController.navigate(WalletGraph) {
                            popUpTo(AuthGraph) { inclusive = true }
                        }
                    }
                )

                walletGraph(
                    navController = navController,
                    onNavigateToSendMoney = { navController.navigate(TransferGraph) },
                    onNavigateToScanQr = { navController.navigate(QrScannerRoute) },
                    onNavigateToRequestMoney = { /* TODO */ },
                    onNavigateToReversal = { /* TODO */ }
                )

                marketplaceGraph(
                    navController = navController
                )

                messagingGraph(
                    navController = navController
                )

                paymentGraph(
                    navController = navController
                )

                transferGraph(
                    navController = navController,
                    onNavigateToTransactionDetail = { transactionId ->
                        navController.navigate(TransactionDetailRoute(transactionId))
                    }
                )
            }
        }
    }
}
