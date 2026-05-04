package com.example.ndugu.routes

import com.example.ndugu.service.TransferService
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
data class SendMoneyRequest(val recipientPhone: String, val amount: Double, val memo: String? = null)

fun Route.transferRoutes(transferService: TransferService) {
    authenticate("auth-jwt") {
        route("/api/transfers") {
            post("/send") {
                val principal = call.principal<JWTPrincipal>()
                val senderId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<SendMoneyRequest>()
                if (request.amount <= 0) return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid amount"))

                val success = transferService.sendMoney(senderId, request.recipientPhone, BigDecimal.valueOf(request.amount), request.memo)
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Transfer successful"))
                } else {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Transfer failed. Check balance or recipient."))
                }
            }
        }
    }
}
