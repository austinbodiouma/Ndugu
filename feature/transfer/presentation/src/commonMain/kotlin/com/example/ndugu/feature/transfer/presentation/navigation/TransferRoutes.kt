package com.example.ndugu.feature.transfer.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object TransferGraph

@Serializable
data object ContactPickerRoute

@Serializable
data class SendMoneyRoute(
    val recipientPhone: String,
    val recipientName: String,
)

@Serializable
data class TransferReversalRoute(val transactionId: String)
