package com.example.ndugu.feature.marketplace.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListingDto(
    val id: String,
    val sellerId: String,
    val sellerName: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrls: List<String>,
    val status: String,
    val createdAt: String
)

@Serializable
data class CreateListingRequest(
    val title: String,
    val description: String,
    val price: Double,
    val category: String
)
