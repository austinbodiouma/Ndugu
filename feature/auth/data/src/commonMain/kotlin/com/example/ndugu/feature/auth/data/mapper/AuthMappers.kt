package com.example.ndugu.feature.auth.data.mapper

import com.example.ndugu.feature.auth.data.remote.dto.AuthDto
import com.example.ndugu.feature.auth.data.remote.dto.StudentDto
import com.example.ndugu.feature.auth.domain.model.AuthToken
import com.example.ndugu.feature.auth.domain.model.Student

fun StudentDto.toStudent(): Student {
    return Student(
        id = id,
        phone = phone,
        email = email,
        fullName = fullName,
        isVerified = isVerified
    )
}

fun AuthDto.toAuthToken(): AuthToken {
    return AuthToken(
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}
