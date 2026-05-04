package com.example.ndugu.feature.transfer.data.remote

import com.example.ndugu.core.data.networking.safePost
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.transfer.data.remote.dto.TransferRequestDto
import com.example.ndugu.feature.transfer.data.remote.dto.TransferResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

class TransferRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun sendMoney(request: TransferRequestDto): Result<TransferResponseDto, DataError.Network> {
        return httpClient.safePost("/api/wallet/transfer") {
            setBody(request)
        }
    }
}
