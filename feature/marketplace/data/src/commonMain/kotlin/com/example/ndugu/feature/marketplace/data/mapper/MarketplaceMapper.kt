package com.example.ndugu.feature.marketplace.data.mapper

import com.example.ndugu.feature.marketplace.data.remote.dto.ListingDto
import com.example.ndugu.feature.marketplace.domain.model.Listing
import com.example.ndugu.feature.marketplace.domain.model.ListingStatus

fun ListingDto.toDomain(): Listing {
    return Listing(
        id = id,
        sellerId = sellerId,
        sellerName = sellerName,
        title = title,
        description = description,
        price = price,
        category = category,
        imageUrls = imageUrls,
        status = when (status) {
            "AVAILABLE" -> ListingStatus.AVAILABLE
            "SOLD" -> ListingStatus.SOLD
            "ESCROW" -> ListingStatus.ESCROW
            else -> ListingStatus.AVAILABLE
        },
        createdAt = createdAt
    )
}
