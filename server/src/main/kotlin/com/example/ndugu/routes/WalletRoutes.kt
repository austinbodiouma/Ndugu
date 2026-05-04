package com.example.ndugu.routes

import com.example.ndugu.service.WalletService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.walletRoutes(walletService: WalletService) {
    authenticate("auth-jwt") {
        route("/api/wallet") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)
                
                val wallet = walletService.getWallet(studentId)
                if (wallet != null) {
                    call.respond(wallet)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Wallet not found"))
                }
            }

            get("/transactions") {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)
                
                val limit = call.parameters["limit"]?.toIntOrNull() ?: 20
                val offset = call.parameters["offset"]?.toLongOrNull() ?: 0
                
                val transactions = walletService.getTransactions(studentId, limit, offset)
                call.respond(transactions)
            }
        }
    }
}
