package com.example.ndugu.security

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtService {
    private const val ISSUER = "ndugu-server"
    private const val AUDIENCE = "ndugu-app"
    private val SECRET = System.getenv("JWT_SECRET") ?: "very-secret-key"
    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateToken(studentId: String): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("id", studentId)
            .withExpiresAt(Date(System.currentTimeMillis() + 1 * 60 * 60 * 1000)) // 1 hour
            .sign(algorithm)
    }

    fun generateRefreshToken(studentId: String): String {
        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("id", studentId)
            .withExpiresAt(Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)) // 30 days
            .sign(algorithm)
    }

    fun verifier() = JWT.require(algorithm)
        .withAudience(AUDIENCE)
        .withIssuer(ISSUER)
        .build()
}

object PasswordHasher {
    fun hash(password: String): String = BCrypt.withDefaults().hashToString(12, password.toCharArray())
    fun verify(password: String, hash: String): Boolean = BCrypt.verifyer().verify(password.toCharArray(), hash).verified
}
