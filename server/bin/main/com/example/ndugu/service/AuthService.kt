package com.example.ndugu.service

import com.example.ndugu.data.DatabaseFactory.dbQuery
import com.example.ndugu.data.RefreshTokensTable
import com.example.ndugu.data.StudentsTable
import com.example.ndugu.data.WalletsTable
import com.example.ndugu.security.JwtService
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.util.*

class AuthService {

    suspend fun register(phone: String, email: String, fullName: String, passwordHash: String): String = dbQuery {
        val studentId = UUID.randomUUID().toString()
        val otp = (100000..999999).random().toString()
        val now = Clock.System.now()
        val otpExpiry = now.plus(5, DateTimeUnit.MINUTE)

        // 1. Create Student
        StudentsTable.insert {
            it[id] = studentId
            it[StudentsTable.phone] = phone
            it[StudentsTable.email] = email
            it[StudentsTable.fullName] = fullName
            it[StudentsTable.passwordHash] = passwordHash
            it[isVerified] = false
            it[StudentsTable.otp] = otp
            it[otpExpiresAt] = otpExpiry
            it[createdAt] = now
            it[updatedAt] = now
        }

        // 2. Create Wallet for Student
        WalletsTable.insert {
            it[id] = UUID.randomUUID().toString()
            it[WalletsTable.studentId] = studentId
            it[balance] = BigDecimal.ZERO
            it[currency] = "KES"
            it[createdAt] = now
            it[updatedAt] = now
        }

        studentId
    }

    suspend fun login(phone: String, passwordHash: String): Map<String, String>? = dbQuery {
        val userRow = StudentsTable.selectAll()
            .where { (StudentsTable.phone eq phone) and (StudentsTable.passwordHash eq passwordHash) }
            .singleOrNull()

        if (userRow != null) {
            val studentId = userRow[StudentsTable.id]
            val accessToken = JwtService.generateToken(studentId)
            val refreshToken = UUID.randomUUID().toString()

            // Store refresh token
            val now = Clock.System.now()
            RefreshTokensTable.insert {
                it[id] = UUID.randomUUID().toString()
                it[RefreshTokensTable.studentId] = studentId
                it[tokenHash] = refreshToken // In production, hash this
                it[expiresAt] = now.plus(30, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
                it[createdAt] = now
            }

            mapOf(
                "accessToken" to accessToken,
                "refreshToken" to refreshToken,
                "studentId" to studentId
            )
        } else null
    }

    suspend fun verifyOtp(phone: String, otp: String): Boolean = dbQuery {
        val now = Clock.System.now()
        val userRow = StudentsTable.selectAll()
            .where { (StudentsTable.phone eq phone) and (StudentsTable.otp eq otp) }
            .singleOrNull()

        if (userRow != null) {
            val expiry = userRow[StudentsTable.otpExpiresAt]
            if (expiry != null && expiry > now) {
                val studentId = userRow[StudentsTable.id]
                StudentsTable.update({ StudentsTable.id eq studentId }) {
                    it[isVerified] = true
                    it[StudentsTable.otp] = null
                    it[otpExpiresAt] = null
                    it[updatedAt] = now
                }
                true
            } else false
        } else false
    }

    suspend fun resendOtp(phone: String): Boolean = dbQuery {
        val otp = (100000..999999).random().toString()
        val now = Clock.System.now()
        val otpExpiry = now.plus(5, DateTimeUnit.MINUTE)

        StudentsTable.update({ StudentsTable.phone eq phone }) {
            it[StudentsTable.otp] = otp
            it[otpExpiresAt] = otpExpiry
            it[updatedAt] = now
        }
        true
    }
}
