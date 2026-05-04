package com.example.ndugu.feature.auth.data.repository

import com.example.ndugu.core.data.auth.TokenStorage
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.core.domain.util.map
import com.example.ndugu.core.domain.util.onSuccess
import com.example.ndugu.feature.auth.data.mapper.toDomain
import com.example.ndugu.feature.auth.data.remote.KtorAuthRemoteDataSource
import com.example.ndugu.feature.auth.data.remote.dto.*
import com.example.ndugu.feature.auth.domain.model.AuthToken
import com.example.ndugu.feature.auth.domain.model.Student
import com.example.ndugu.feature.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: KtorAuthRemoteDataSource,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    private var currentPhone: String? = null

    override suspend fun register(
        phone: String,
        email: String,
        fullName: String,
        password: String
    ): Result<Student, DataError.Network> {
        val request = RegisterRequest(phone, email, fullName, password)
        val result = remoteDataSource.register(request)
        return when (result) {
            is Result.Success -> {
                currentPhone = phone
                Result.Success(
                    Student(
                        id = result.data.id,
                        phone = phone,
                        email = email,
                        fullName = fullName,
                        isVerified = false
                    )
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    override suspend fun login(
        phone: String,
        password: String
    ): Result<AuthToken, DataError.Network> {
        val request = LoginRequest(phone, password)
        return remoteDataSource.login(request)
            .onSuccess { dto ->
                tokenStorage.saveAuthTokens(
                    accessToken = dto.accessToken,
                    refreshToken = dto.refreshToken,
                    userId = dto.studentId
                )
            }
            .map { it.toDomain() }
    }

    override suspend fun verifyOtp(otp: String): Result<AuthToken, DataError.Network> {
        val phone = currentPhone ?: return Result.Error(DataError.Network.UNKNOWN)
        val request = VerifyOtpRequest(phone, otp)
        return remoteDataSource.verifyOtp(request)
            .onSuccess { dto ->
                tokenStorage.saveAuthTokens(
                    accessToken = dto.accessToken,
                    refreshToken = dto.refreshToken,
                    userId = dto.studentId
                )
                tokenStorage.isVerified = true
            }
            .map { it.toDomain() }
    }

    override suspend fun resendOtp(phone: String): Result<Unit, DataError.Network> {
        return remoteDataSource.resendOtp(ResendOtpRequest(phone))
    }

    override suspend fun uploadStudentId(idBytes: ByteArray): Result<Unit, DataError.Network> {
        return remoteDataSource.uploadStudentId(idBytes)
    }

    override suspend fun logout(): Result<Unit, DataError.Local> {
        tokenStorage.clearAll()
        return Result.Success(Unit)
    }
}
