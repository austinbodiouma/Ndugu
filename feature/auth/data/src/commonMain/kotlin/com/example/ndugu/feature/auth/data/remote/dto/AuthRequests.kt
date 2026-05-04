package com.example.ndugu.feature.auth.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val phone: String,
    val email: String,
    val fullName: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val phone: String,
    val password: String
)

@Serializable
data class VerifyOtpRequest(
    val phone: String,
    val otp: String
)

@Serializable
data class ResendOtpRequest(
    val phone: String
)

@Serializable
data class RefreshRequest(
    val refreshToken: String
)
