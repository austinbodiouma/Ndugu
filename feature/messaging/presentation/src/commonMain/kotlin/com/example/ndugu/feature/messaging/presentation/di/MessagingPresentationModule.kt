package com.example.ndugu.feature.messaging.presentation.di

import com.example.ndugu.feature.messaging.presentation.history.ChatHistoryViewModel
import com.example.ndugu.feature.messaging.presentation.room.ChatRoomViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val MessagingPresentationModule = module {
    viewModelOf(::ChatHistoryViewModel)
    viewModelOf(::ChatRoomViewModel)
}
