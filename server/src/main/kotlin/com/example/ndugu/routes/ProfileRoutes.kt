package com.example.ndugu.routes

import com.example.ndugu.data.DatabaseFactory.dbQuery
import com.example.ndugu.data.StudentsTable
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

@Serializable
data class UpdateProfileRequest(val fullName: String?, val email: String?)

fun Route.profileRoutes() {
    authenticate("auth-jwt") {
        route("/api/profile") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)
                
                val userRow = dbQuery {
                    StudentsTable.selectAll().where { StudentsTable.id eq studentId }.singleOrNull()
                }

                if (userRow != null) {
                    call.respond(mapOf(
                        "id" to userRow[StudentsTable.id],
                        "phone" to userRow[StudentsTable.phone],
                        "email" to userRow[StudentsTable.email],
                        "fullName" to userRow[StudentsTable.fullName],
                        "isVerified" to userRow[StudentsTable.isVerified],
                        "verificationStatus" to userRow[StudentsTable.verificationStatus]
                    ))
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            put {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@put call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<UpdateProfileRequest>()
                
                dbQuery {
                    StudentsTable.update({ StudentsTable.id eq studentId }) {
                        request.fullName?.let { name -> it[fullName] = name }
                        request.email?.let { emailVal -> it[email] = emailVal }
                        it[updatedAt] = Clock.System.now()
                    }
                }
                
                call.respond(HttpStatusCode.OK, mapOf("message" to "Profile updated"))
            }
        }
    }
}
