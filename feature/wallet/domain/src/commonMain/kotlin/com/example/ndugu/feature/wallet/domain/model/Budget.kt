package com.example.ndugu.feature.wallet.domain.model

data class Budget(
    val id: String,
    val category: String,
    val limitAmount: Double,
    val spentAmount: Double,
    val period: String
)
