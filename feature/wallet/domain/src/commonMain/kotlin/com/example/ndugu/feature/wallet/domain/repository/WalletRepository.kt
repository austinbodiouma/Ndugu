package com.example.ndugu.feature.wallet.domain.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.wallet.domain.model.Budget
import com.example.ndugu.feature.wallet.domain.model.Transaction
import com.example.ndugu.feature.wallet.domain.model.Wallet

interface WalletRepository {
    suspend fun getWallet(): Result<Wallet, DataError.Network>
    suspend fun getTransactions(): Result<List<Transaction>, DataError.Network>
    suspend fun getBudgets(): Result<List<Budget>, DataError.Network>
    suspend fun setBudget(category: String, limitAmount: Double): Result<Budget, DataError.Network>
}
