package com.example.ndugu.feature.auth.data.remote

import com.example.ndugu.core.data.networking.safePost
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.auth.data.remote.dto.*
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

/**
 * Implementation of remote authentication data operations using Ktor Client.
 */
class KtorAuthRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun register(request: RegisterRequest): Result<RegisterResponseDto, DataError.Network> {
        return httpClient.safePost("/api/auth/register") {
            setBody(request)
        }
    }

    suspend fun login(request: LoginRequest): Result<AuthResponseDto, DataError.Network> {
        return httpClient.safePost("/api/auth/login") {
            setBody(request)
        }
    }

    suspend fun verifyOtp(request: VerifyOtpRequest): Result<AuthResponseDto, DataError.Network> {
        return httpClient.safePost("/api/auth/verify-otp") {
            setBody(request)
        }
    }

    suspend fun resendOtp(request: ResendOtpRequest): Result<Unit, DataError.Network> {
        return httpClient.safePost("/api/auth/resend-otp") {
            setBody(request)
        }
    }

    suspend fun uploadStudentId(idBytes: ByteArray): Result<Unit, DataError.Network> {
        return httpClient.safePost("/api/auth/upload-id") {
            setBody(idBytes)
        }
    }
}
