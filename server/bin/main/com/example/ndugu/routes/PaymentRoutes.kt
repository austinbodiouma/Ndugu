package com.example.ndugu.routes

import com.example.ndugu.service.PaymentService
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
data class QRPaymentRequest(val qrCode: String, val amount: Double)

fun Route.paymentRoutes(paymentService: PaymentService) {
    authenticate("auth-jwt") {
        route("/api/payments") {
            post("/qr") {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<QRPaymentRequest>()
                if (request.amount <= 0) return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid amount"))

                val success = paymentService.processQRPayment(studentId, request.qrCode, BigDecimal.valueOf(request.amount))
                if (success) {
                    call.respond(HttpStatusCode.OK, mapOf("message" to "Payment successful"))
                } else {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Payment failed. Check balance or QR code."))
                }
            }
        }
    }
}
