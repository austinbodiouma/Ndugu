package com.example.ndugu.feature.wallet.domain.model

data class Wallet(
    val id: String,
    val studentId: String,
    val balance: Double,
    val currency: String
)

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val counterpartyName: String,
    val memo: String?,
    val category: String,
    val status: String,
    val createdAt: String
)

enum class TransactionType {
    CREDIT, DEBIT
}
