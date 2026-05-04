package com.example.ndugu.feature.wallet.data.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.core.domain.util.map
import com.example.ndugu.feature.wallet.data.mapper.toDomain
import com.example.ndugu.feature.wallet.data.remote.WalletRemoteDataSource
import com.example.ndugu.feature.wallet.data.remote.dto.SetBudgetRequestDto
import com.example.ndugu.feature.wallet.domain.model.Budget
import com.example.ndugu.feature.wallet.domain.model.Transaction
import com.example.ndugu.feature.wallet.domain.model.Wallet
import com.example.ndugu.feature.wallet.domain.repository.WalletRepository

class WalletRepositoryImpl(
    private val remoteDataSource: WalletRemoteDataSource
) : WalletRepository {

    override suspend fun getWallet(): Result<Wallet, DataError.Network> {
        return remoteDataSource.getWallet().map { it.toDomain() }
    }

    override suspend fun getTransactions(): Result<List<Transaction>, DataError.Network> {
        return remoteDataSource.getTransactions().map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override suspend fun getBudgets(): Result<List<Budget>, DataError.Network> {
        return remoteDataSource.getBudgets().map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override suspend fun setBudget(category: String, limitAmount: Double): Result<Budget, DataError.Network> {
        return remoteDataSource.setBudget(
            SetBudgetRequestDto(category, limitAmount)
        ).map { it.toDomain() }
    }
}
