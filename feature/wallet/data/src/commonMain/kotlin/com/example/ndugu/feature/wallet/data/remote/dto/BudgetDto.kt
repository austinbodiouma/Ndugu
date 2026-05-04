package com.example.ndugu.feature.wallet.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BudgetDto(
    val id: String,
    val studentId: String,
    val category: String,
    val limitAmount: Double,
    val spentAmount: Double,
    val period: String
)

@Serializable
data class SetBudgetRequestDto(
    val category: String,
    val limitAmount: Double
)
