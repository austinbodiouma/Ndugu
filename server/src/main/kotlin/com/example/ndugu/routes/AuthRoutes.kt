package com.example.ndugu.routes

import com.example.ndugu.service.AuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(val phone: String, val email: String, val fullName: String, val password: String)

@Serializable
data class LoginRequest(val phone: String, val password: String)

@Serializable
data class VerifyOtpRequest(val phone: String, val otp: String)

@Serializable
data class ResendOtpRequest(val phone: String)

fun Route.authRoutes(authService: AuthService) {
    route("/api/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()
            val studentId = authService.register(request.phone, request.email, request.fullName, request.password)
            if (studentId != null) {
                call.respond(HttpStatusCode.Created, mapOf("id" to studentId))
            } else {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Registration failed"))
            }
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val response = authService.login(request.phone, request.password)
            if (response != null) {
                call.respond(response)
            } else {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
            }
        }

        post("/verify-otp") {
            val request = call.receive<VerifyOtpRequest>()
            val response = authService.verifyOtp(request.phone, request.otp)
            if (response != null) {
                call.respond(response)
            } else {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid or expired OTP"))
            }
        }

        post("/resend-otp") {
            val request = call.receive<ResendOtpRequest>()
            val success = authService.resendOtp(request.phone)
            if (success) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "OTP resent"))
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
            }
        }
    }
}
