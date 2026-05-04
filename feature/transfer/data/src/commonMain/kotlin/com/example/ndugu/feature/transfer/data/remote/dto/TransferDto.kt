package com.example.ndugu.feature.transfer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TransferRequestDto(
    val recipientPhone: String,
    val amount: Double,
    val memo: String? = null
)

@Serializable
data class TransferResponseDto(
    val transactionId: String,
    val message: String
)
