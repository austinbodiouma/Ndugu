package com.example.ndugu.feature.auth.domain.model

/**
 * Domain representation of a student user.
 */
data class Student(
    val id: String,
    val phone: String,
    val email: String,
    val fullName: String,
    val isVerified: Boolean
)
