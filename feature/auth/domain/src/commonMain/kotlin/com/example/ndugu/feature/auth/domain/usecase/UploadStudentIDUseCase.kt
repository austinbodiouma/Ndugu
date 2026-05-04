package com.example.ndugu.feature.auth.domain.usecase

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.auth.domain.repository.AuthRepository

class UploadStudentIDUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        idBytes: ByteArray
    ): Result<Unit, DataError.Network> {
        return repository.uploadStudentId(idBytes)
    }
}
