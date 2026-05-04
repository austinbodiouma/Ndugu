package com.example.ndugu.feature.auth.domain.usecase

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.auth.domain.model.AuthToken
import com.example.ndugu.feature.auth.domain.repository.AuthRepository

class VerifyOTPUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        otp: String
    ): Result<AuthToken, DataError.Network> {
        return repository.verifyOtp(otp)
    }
}
