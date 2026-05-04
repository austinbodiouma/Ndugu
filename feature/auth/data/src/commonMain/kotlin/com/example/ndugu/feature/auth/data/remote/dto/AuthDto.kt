package com.example.ndugu.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class StudentDto(
    val id: String,
    val phone: String,
    val email: String,
    val fullName: String,
    val isVerified: Boolean,
    val verificationStatus: String
)

@Serializable
data class AuthResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val studentId: String
)
