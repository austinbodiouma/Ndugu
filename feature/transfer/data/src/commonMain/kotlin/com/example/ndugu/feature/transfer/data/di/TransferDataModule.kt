package com.example.ndugu.feature.transfer.data.di

import com.example.ndugu.feature.transfer.data.remote.TransferRemoteDataSource
import com.example.ndugu.feature.transfer.data.repository.TransferRepositoryImpl
import com.example.ndugu.feature.transfer.domain.repository.TransferRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val TransferDataModule = module {
    singleOf(::TransferRemoteDataSource)
    singleOf(::TransferRepositoryImpl) bind TransferRepository::class
}
