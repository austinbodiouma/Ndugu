package com.example.ndugu.feature.marketplace.data.repository

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import com.example.ndugu.core.domain.util.map
import com.example.ndugu.feature.marketplace.data.mapper.toDomain
import com.example.ndugu.feature.marketplace.data.remote.MarketplaceRemoteDataSource
import com.example.ndugu.feature.marketplace.data.remote.dto.CreateListingRequest
import com.example.ndugu.feature.marketplace.domain.model.Listing
import com.example.ndugu.feature.marketplace.domain.repository.MarketplaceRepository

class MarketplaceRepositoryImpl(
    private val remoteDataSource: MarketplaceRemoteDataSource
) : MarketplaceRepository {

    override suspend fun getListings(
        query: String?,
        category: String?
    ): Result<List<Listing>, DataError.Network> {
        return remoteDataSource.getListings(query, category).map { dtos ->
            dtos.map { it.toDomain() }
        }
    }

    override suspend fun createListing(
        title: String,
        description: String,
        price: Double,
        category: String
    ): Result<Listing, DataError.Network> {
        return remoteDataSource.createListing(
            CreateListingRequest(title, description, price, category)
        ).map { it.toDomain() }
    }

    override suspend fun getListingDetails(id: String): Result<Listing, DataError.Network> {
        return remoteDataSource.getListingDetails(id).map { it.toDomain() }
    }
}
