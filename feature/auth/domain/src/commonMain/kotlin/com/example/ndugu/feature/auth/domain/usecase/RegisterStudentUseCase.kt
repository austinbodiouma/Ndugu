package com.example.ndugu.feature.auth.domain.usecase

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.auth.domain.model.Student
import com.example.ndugu.feature.auth.domain.repository.AuthRepository

class RegisterStudentUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        phone: String,
        email: String,
        fullName: String,
        password: String
    ): Result<Student, DataError.Network> {
        return repository.register(phone, email, fullName, password)
    }
}
