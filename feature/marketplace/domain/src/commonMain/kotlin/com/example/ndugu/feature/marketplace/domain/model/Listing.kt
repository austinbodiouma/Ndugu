package com.example.ndugu.feature.marketplace.domain.model

data class Listing(
    val id: String,
    val sellerId: String,
    val sellerName: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrls: List<String>,
    val status: ListingStatus,
    val createdAt: String
)

enum class ListingStatus {
    AVAILABLE, SOLD, ESCROW
}
