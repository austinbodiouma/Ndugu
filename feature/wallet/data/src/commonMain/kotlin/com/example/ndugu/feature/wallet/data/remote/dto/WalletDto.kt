package com.example.ndugu.feature.wallet.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WalletDto(
    val id: String,
    val studentId: String,
    val balance: Double,
    val currency: String
)

@Serializable
data class TransactionDto(
    val id: String,
    val walletId: String,
    val type: String,
    val amount: Double,
    val counterpartyName: String,
    val memo: String?,
    val category: String,
    val status: String,
    val referenceType: String?,
    val referenceId: String?,
    val createdAt: String
)
