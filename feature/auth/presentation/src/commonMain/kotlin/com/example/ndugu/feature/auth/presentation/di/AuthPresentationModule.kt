package com.example.ndugu.feature.auth.presentation.di

import com.example.ndugu.feature.auth.presentation.login.LoginViewModel
import com.example.ndugu.feature.auth.presentation.otp.OtpVerifyViewModel
import com.example.ndugu.feature.auth.presentation.register.RegisterViewModel
import com.example.ndugu.feature.auth.presentation.uploadid.UploadIdViewModel
import com.example.ndugu.feature.auth.presentation.verificationpending.VerificationPendingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val AuthPresentationModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::OtpVerifyViewModel)
    viewModelOf(::UploadIdViewModel)
    viewModelOf(::VerificationPendingViewModel)
}
