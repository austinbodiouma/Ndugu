package com.example.ndugu.feature.wallet.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object WalletDashboardRoute

@Serializable
data object TransactionHistoryRoute

@Serializable
data class TransactionDetailRoute(val transactionId: String)

@Serializable
data class ReversalRoute(val transactionId: String)

@Serializable
data object RequestMoneyRoute

@Serializable
data object BudgetRoute

@Serializable
data object ProfileSettingsRoute

@Serializable
data object QrScannerRoute
