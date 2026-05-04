package com.example.ndugu.feature.marketplace.data.remote

import com.example.ndugu.core.data.networking.safeGet
import com.example.ndugu.core.data.networking.safePost
import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.feature.marketplace.data.remote.dto.CreateListingRequest
import com.example.ndugu.feature.marketplace.data.remote.dto.ListingDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

class MarketplaceRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getListings(
        query: String? = null,
        category: String? = null
    ): Result<List<ListingDto>, DataError.Network> {
        return httpClient.safeGet("/api/marketplace/listings") {
            query?.let { parameter("query", it) }
            category?.let { parameter("category", it) }
        }
    }

    suspend fun createListing(request: CreateListingRequest): Result<ListingDto, DataError.Network> {
        return httpClient.safePost("/api/marketplace/listings") {
            setBody(request)
        }
    }

    suspend fun getListingDetails(id: String): Result<ListingDto, DataError.Network> {
        return httpClient.safeGet("/api/marketplace/listings/$id")
    }
}
