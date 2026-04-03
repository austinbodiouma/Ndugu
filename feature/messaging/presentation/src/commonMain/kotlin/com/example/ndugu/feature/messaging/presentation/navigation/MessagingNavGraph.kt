package com.example.ndugu.feature.messaging.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.ndugu.feature.messaging.presentation.history.ChatHistoryRoot
import com.example.ndugu.feature.messaging.presentation.room.ChatRoomRoot

fun NavGraphBuilder.messagingGraph(
    navController: NavController,
) {
    navigation<MessagingRoute.ChatHistoryRoute>(
        startDestination = MessagingRoute.ChatHistoryRoute
    ) {
        composable<MessagingRoute.ChatHistoryRoute> {
            ChatHistoryRoot(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToChat = { chatId ->
                    navController.navigate(MessagingRoute.ChatRoomRoute)
                },
                onNavigateToCompose = {
                    // TODO: Implement navigation to compose screen
                }
            )
        }
        composable<MessagingRoute.ChatRoomRoute> {
            ChatRoomRoot(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
