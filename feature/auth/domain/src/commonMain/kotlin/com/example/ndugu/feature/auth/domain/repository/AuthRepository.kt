package com.example.ndugu.feature.auth.domain.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.auth.domain.model.AuthToken
import com.example.ndugu.feature.auth.domain.model.Student

/**
 * Contract for authentication-related data operations.
 */
interface AuthRepository {
    
    suspend fun register(
        phone: String,
        email: String,
        fullName: String,
        password: String
    ): Result<Student, DataError.Network>

    suspend fun login(
        phone: String,
        password: String
    ): Result<AuthToken, DataError.Network>

    suspend fun verifyOtp(
        otp: String
    ): Result<AuthToken, DataError.Network>

    suspend fun resendOtp(
        phone: String
    ): Result<Unit, DataError.Network>

    suspend fun uploadStudentId(
        idBytes: ByteArray
    ): Result<Unit, DataError.Network>

    suspend fun logout(): Result<Unit, DataError.Local>
}
