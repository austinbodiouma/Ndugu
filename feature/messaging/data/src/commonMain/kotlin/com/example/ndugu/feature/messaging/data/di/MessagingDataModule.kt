package com.example.ndugu.feature.messaging.data.di

import com.example.ndugu.feature.messaging.data.remote.MessageRemoteDataSource
import com.example.ndugu.feature.messaging.data.repository.MessageRepositoryImpl
import com.example.ndugu.feature.messaging.domain.repository.MessageRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val MessagingDataModule = module {
    singleOf(::MessageRemoteDataSource)
    singleOf(::MessageRepositoryImpl) bind MessageRepository::class
}
