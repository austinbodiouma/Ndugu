package com.example.ndugu.feature.auth.domain.model

/**
 * Domain representation of an authentication token pair.
 */
data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)
