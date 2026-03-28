package com.example.ndugu.feature.auth.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute

@Serializable
data class OtpVerifyRoute(val phone: String)

@Serializable
data object UploadIdRoute
