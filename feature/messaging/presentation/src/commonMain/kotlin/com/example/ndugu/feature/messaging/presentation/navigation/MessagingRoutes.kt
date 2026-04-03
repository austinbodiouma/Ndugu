package com.example.ndugu.feature.messaging.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MessagingRoute {
@Serializable
    data object ChatHistoryRoute : MessagingRoute

    @Serializable
    data object ChatRoomRoute : MessagingRoute
}
