package com.example.ndugu.feature.wallet.data.remote

import com.example.ndugu.core.data.networking.safeGet
import com.example.ndugu.core.data.networking.safePost
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.wallet.data.remote.dto.*
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

class WalletRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getWallet(): Result<WalletDto, DataError.Network> {
        return httpClient.safeGet("/api/wallet")
    }

    suspend fun getTransactions(): Result<List<TransactionDto>, DataError.Network> {
        return httpClient.safeGet("/api/wallet/transactions")
    }

    suspend fun getBudgets(): Result<List<BudgetDto>, DataError.Network> {
        return httpClient.safeGet("/api/budgets")
    }

    suspend fun setBudget(request: SetBudgetRequestDto): Result<BudgetDto, DataError.Network> {
        return httpClient.safePost("/api/budgets") {
            setBody(request)
        }
    }
}
