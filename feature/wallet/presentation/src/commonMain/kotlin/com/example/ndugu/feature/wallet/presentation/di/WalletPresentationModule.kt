package com.example.ndugu.feature.wallet.presentation.di

import com.example.ndugu.feature.wallet.presentation.budget.BudgetViewModel
import com.example.ndugu.feature.wallet.presentation.dashboard.WalletDashboardViewModel
import com.example.ndugu.feature.wallet.presentation.history.TransactionHistoryViewModel
import com.example.ndugu.feature.wallet.presentation.profile.ProfileSettingsViewModel
import com.example.ndugu.feature.wallet.presentation.qrscanner.QrScannerViewModel
import com.example.ndugu.feature.wallet.presentation.transactiondetail.TransactionDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val WalletPresentationModule = module {
    viewModelOf(::WalletDashboardViewModel)
    viewModelOf(::TransactionHistoryViewModel)
    viewModelOf(::TransactionDetailViewModel)
    viewModelOf(::BudgetViewModel)
    viewModelOf(::ProfileSettingsViewModel)
    viewModelOf(::QrScannerViewModel)
}
