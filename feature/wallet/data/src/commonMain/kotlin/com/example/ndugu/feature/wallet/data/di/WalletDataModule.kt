package com.example.ndugu.feature.wallet.data.di

import com.example.ndugu.feature.wallet.data.remote.WalletRemoteDataSource
import com.example.ndugu.feature.wallet.data.repository.WalletRepositoryImpl
import com.example.ndugu.feature.wallet.domain.repository.WalletRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val WalletDataModule = module {
    singleOf(::WalletRemoteDataSource)
    singleOf(::WalletRepositoryImpl) bind WalletRepository::class
}
