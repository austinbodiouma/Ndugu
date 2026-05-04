package com.example.ndugu.feature.marketplace.domain.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.marketplace.domain.model.Listing

interface MarketplaceRepository {
    suspend fun getListings(
        query: String? = null,
        category: String? = null
    ): Result<List<Listing>, DataError.Network>

    suspend fun createListing(
        title: String,
        description: String,
        price: Double,
        category: String
    ): Result<Listing, DataError.Network>

    suspend fun getListingDetails(id: String): Result<Listing, DataError.Network>
}
