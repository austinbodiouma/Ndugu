package com.example.ndugu.feature.transfer.data.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.core.domain.util.map
import com.example.ndugu.feature.transfer.data.remote.TransferRemoteDataSource
import com.example.ndugu.feature.transfer.data.remote.dto.TransferRequestDto
import com.example.ndugu.feature.transfer.domain.repository.TransferRepository

class TransferRepositoryImpl(
    private val remoteDataSource: TransferRemoteDataSource
) : TransferRepository {

    override suspend fun sendMoney(
        recipientPhone: String,
        amount: Double,
        memo: String?
    ): Result<String, DataError.Network> {
        return remoteDataSource.sendMoney(
            TransferRequestDto(recipientPhone, amount, memo)
        ).map { it.transactionId }
    }
}
