package com.example.ndugu

import com.example.ndugu.data.DatabaseFactory
import com.example.ndugu.routes.*
import com.example.ndugu.security.JwtService
import com.example.ndugu.service.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: SERVER_PORT
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // 1. Initialize Database
    DatabaseFactory.init()

    // 2. Install Features
    install(ContentNegotiation) {
        json(Json { 
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtService.verifier())
            validate { credential ->
                if (credential.payload.getClaim("id").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

    // 3. Initialize Services
    val authService = AuthService()
    val walletService = WalletService()
    val transferService = TransferService()
    val marketplaceService = MarketplaceService()
    val messageService = MessageService()
    val paymentService = PaymentService()
    val budgetService = BudgetService()
    
    // 4. Routing
    routing {
        get("/") {
            call.respondText("Ndugu API is live!")
        }

        get("/health") {
            call.respondText("OK")
        }
        
        authRoutes(authService)
        walletRoutes(walletService)
        transferRoutes(transferService)
        marketplaceRoutes(marketplaceService)
        messageRoutes(messageService)
        paymentRoutes(paymentService)
        budgetRoutes(budgetService)
        profileRoutes()
    }
}