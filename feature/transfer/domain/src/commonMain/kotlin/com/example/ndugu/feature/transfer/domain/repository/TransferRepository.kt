package com.example.ndugu.feature.transfer.domain.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result

interface TransferRepository {
    suspend fun sendMoney(
        recipientPhone: String,
        amount: Double,
        memo: String?
    ): Result<String, DataError.Network>
}
