package com.example.ndugu.feature.payment.presentation.di

import com.example.ndugu.feature.payment.presentation.scanner.QRScannerViewModel
import com.example.ndugu.feature.payment.presentation.confirmation.QRPaymentConfirmationViewModel
import com.example.ndugu.feature.payment.presentation.topup.TopUpViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val PaymentPresentationModule = module {
    viewModelOf(::QRScannerViewModel)
    viewModelOf(::QRPaymentConfirmationViewModel)
    viewModelOf(::TopUpViewModel)
}
