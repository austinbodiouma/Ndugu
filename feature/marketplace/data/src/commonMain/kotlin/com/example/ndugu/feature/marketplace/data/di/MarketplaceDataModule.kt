package com.example.ndugu.feature.marketplace.data.di

import com.example.ndugu.feature.marketplace.data.remote.MarketplaceRemoteDataSource
import com.example.ndugu.feature.marketplace.data.repository.MarketplaceRepositoryImpl
import com.example.ndugu.feature.marketplace.domain.repository.MarketplaceRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val MarketplaceDataModule = module {
    singleOf(::MarketplaceRemoteDataSource)
    singleOf(::MarketplaceRepositoryImpl) bind MarketplaceRepository::class
}
