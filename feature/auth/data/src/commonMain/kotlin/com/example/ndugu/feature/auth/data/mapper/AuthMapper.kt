package com.example.ndugu.feature.auth.data.mapper

import com.example.ndugu.feature.auth.data.remote.dto.AuthResponseDto
import com.example.ndugu.feature.auth.data.remote.dto.StudentDto
import com.example.ndugu.feature.auth.domain.model.AuthToken
import com.example.ndugu.feature.auth.domain.model.Student

fun AuthResponseDto.toDomain(): AuthToken {
    return AuthToken(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun StudentDto.toDomain(): Student {
    return Student(
        id = id,
        phone = phone,
        email = email,
        fullName = fullName,
        isVerified = isVerified
    )
}
