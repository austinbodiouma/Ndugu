package com.example.ndugu.service

import com.example.ndugu.data.*
import com.example.ndugu.data.DatabaseFactory.dbQuery
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.util.*

class MarketplaceService {

    suspend fun getAllListings(category: String? = null, query: String? = null): List<Map<String, Any>> = dbQuery {
        var baseQuery = ListingsTable.selectAll().where { ListingsTable.status eq "ACTIVE" }
        
        category?.let { cat -> 
            baseQuery = baseQuery.andWhere { ListingsTable.category eq cat } 
        }
        
        query?.let { q -> 
            val search = "%${q.lowercase()}%"
            baseQuery = baseQuery.andWhere { 
                (ListingsTable.title.lowerCase() like search) or (ListingsTable.description.lowerCase() like search)
            } 
        }

        baseQuery.orderBy(ListingsTable.createdAt, SortOrder.DESC).map { row ->
            mapOf(
                "id" to row[ListingsTable.id],
                "sellerId" to row[ListingsTable.sellerId],
                "title" to row[ListingsTable.title],
                "description" to row[ListingsTable.description],
                "price" to row[ListingsTable.price].toDouble(),
                "category" to row[ListingsTable.category],
                "imageUrls" to row[ListingsTable.imageUrls].split(","),
                "createdAt" to row[ListingsTable.createdAt].toString()
            )
        }
    }

    suspend fun createListing(sellerId: String, title: String, description: String, price: BigDecimal, category: String, imageUrls: List<String>): String = dbQuery {
        val id = UUID.randomUUID().toString()
        val now = Clock.System.now()
        
        ListingsTable.insert {
            it[this.id] = id
            it[this.sellerId] = sellerId
            it[this.title] = title
            it[this.description] = description
            it[this.price] = price
            it[this.category] = category
            it[this.imageUrls] = imageUrls.joinToString(",")
            it[this.createdAt] = now
            it[this.updatedAt] = now
        }
        id
    }

    suspend fun placeOrder(buyerId: String, listingId: String, quantity: Int): String? = dbQuery {
        val listing = ListingsTable.selectAll().where { ListingsTable.id eq listingId }.singleOrNull() ?: return@dbQuery null
        if (listing[ListingsTable.status] != "ACTIVE") return@dbQuery null
        if (listing[ListingsTable.stockQuantity] < quantity) return@dbQuery null

        val totalAmount = listing[ListingsTable.price] * BigDecimal(quantity)
        
        // Check buyer balance
        val buyerWallet = WalletsTable.selectAll().where { WalletsTable.studentId eq buyerId }.singleOrNull() ?: return@dbQuery null
        if (buyerWallet[WalletsTable.balance] < totalAmount) return@dbQuery null

        val orderId = UUID.randomUUID().toString()
        val now = Clock.System.now()

        // 1. Create Order
        OrdersTable.insert {
            it[id] = orderId
            it[this.listingId] = listingId
            it[this.buyerId] = buyerId
            it[this.sellerId] = listing[ListingsTable.sellerId]
            it[this.quantity] = quantity
            it[this.totalAmount] = totalAmount
            it[this.status] = "PAID" // Simulating immediate payment to escrow
            it[this.escrowAmount] = totalAmount
            it[this.createdAt] = now
            it[this.updatedAt] = now
        }

        // 2. Debit Buyer Wallet (Escrow)
        WalletsTable.update({ WalletsTable.id eq buyerWallet[WalletsTable.id] }) {
            it[balance] = buyerWallet[WalletsTable.balance] - totalAmount
            it[updatedAt] = now
        }

        // 3. Create Transaction Record
        TransactionsTable.insert {
            it[id] = UUID.randomUUID().toString()
            it[walletId] = buyerWallet[WalletsTable.id]
            it[type] = "DEBIT"
            it[amount] = totalAmount
            it[counterpartyName] = "Marketplace Escrow: ${listing[ListingsTable.title]}"
            it[memo] = "Order #$orderId"
            it[category] = "MARKETPLACE"
            it[status] = "ESCROW"
            it[referenceType] = "ORDER"
            it[referenceId] = orderId
            it[createdAt] = now
        }

        // 4. Update Stock
        ListingsTable.update({ ListingsTable.id eq listingId }) {
            it[stockQuantity] = listing[ListingsTable.stockQuantity] - quantity
            if (listing[ListingsTable.stockQuantity] - quantity <= 0) {
                it[status] = "SOLD"
            }
            it[updatedAt] = now
        }

        orderId
    }
}
