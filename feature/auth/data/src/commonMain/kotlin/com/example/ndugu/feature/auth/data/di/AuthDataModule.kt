package com.example.ndugu.feature.auth.data.di

import com.example.ndugu.feature.auth.data.remote.KtorAuthRemoteDataSource
import com.example.ndugu.feature.auth.data.repository.AuthRepositoryImpl
import com.example.ndugu.feature.auth.domain.repository.AuthRepository
import com.example.ndugu.feature.auth.domain.usecase.LoginUseCase
import com.example.ndugu.feature.auth.domain.usecase.RegisterStudentUseCase
import com.example.ndugu.feature.auth.domain.usecase.UploadStudentIDUseCase
import com.example.ndugu.feature.auth.domain.usecase.VerifyOTPUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val AuthDataModule = module {
    singleOf(::KtorAuthRemoteDataSource)
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
    
    // Domain Use Cases (Injecting the Repository)
    singleOf(::LoginUseCase)
    singleOf(::RegisterStudentUseCase)
    singleOf(::VerifyOTPUseCase)
    singleOf(::UploadStudentIDUseCase)
}
