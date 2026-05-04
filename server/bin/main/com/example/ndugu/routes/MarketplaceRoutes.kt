package com.example.ndugu.routes

import com.example.ndugu.service.MarketplaceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class CreateListingRequest(val title: String, val description: String, val price: Double, val category: String, val imageUrls: List<String>)

@Serializable
data class PlaceOrderRequest(val listingId: String, val quantity: Int)

fun Route.marketplaceRoutes(marketplaceService: MarketplaceService) {
    route("/api/marketplace") {
        get("/listings") {
            val category = call.request.queryParameters["category"]
            val query = call.request.queryParameters["query"]
            val listings = marketplaceService.getAllListings(category, query)
            call.respond(listings)
        }

        authenticate("auth-jwt") {
            post("/listings") {
                val principal = call.principal<JWTPrincipal>()
                val sellerId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<CreateListingRequest>()
                val id = marketplaceService.createListing(sellerId, request.title, request.description, BigDecimal.valueOf(request.price), request.category, request.imageUrls)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            }

            post("/orders") {
                val principal = call.principal<JWTPrincipal>()
                val buyerId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<PlaceOrderRequest>()
                val orderId = marketplaceService.placeOrder(buyerId, request.listingId, request.quantity)
                if (orderId != null) {
                    call.respond(HttpStatusCode.Created, mapOf("orderId" to orderId))
                } else {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Order failed. Check stock or balance."))
                }
            }
        }
    }
}
