package com.example.ndugu.feature.wallet.data.mapper

import com.example.ndugu.feature.wallet.data.remote.dto.BudgetDto
import com.example.ndugu.feature.wallet.data.remote.dto.TransactionDto
import com.example.ndugu.feature.wallet.data.remote.dto.WalletDto
import com.example.ndugu.feature.wallet.domain.model.Budget
import com.example.ndugu.feature.wallet.domain.model.Transaction
import com.example.ndugu.feature.wallet.domain.model.TransactionType
import com.example.ndugu.feature.wallet.domain.model.Wallet

fun WalletDto.toDomain(): Wallet {
    return Wallet(
        id = id,
        studentId = studentId,
        balance = balance,
        currency = currency
    )
}

fun TransactionDto.toDomain(): Transaction {
    return Transaction(
        id = id,
        type = if (type == "CREDIT") TransactionType.CREDIT else TransactionType.DEBIT,
        amount = amount,
        counterpartyName = counterpartyName,
        memo = memo,
        category = category,
        status = status,
        createdAt = createdAt
    )
}

fun BudgetDto.toDomain(): Budget {
    return Budget(
        id = id,
        category = category,
        limitAmount = limitAmount,
        spentAmount = spentAmount,
        period = period
    )
}
