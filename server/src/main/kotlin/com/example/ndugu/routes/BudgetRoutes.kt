package com.example.ndugu.routes

import com.example.ndugu.service.BudgetService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Serializable
data class SetBudgetRequest(val category: String, val limitAmount: Double, val month: Int? = null, val year: Int? = null)

fun Route.budgetRoutes(budgetService: BudgetService) {
    authenticate("auth-jwt") {
        route("/api/budgets") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@get call.respond(HttpStatusCode.Unauthorized)
                
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val month = call.parameters["month"]?.toIntOrNull() ?: now.monthNumber
                val year = call.parameters["year"]?.toIntOrNull() ?: now.year
                
                val budgets = budgetService.getBudgets(studentId, month, year)
                call.respond(budgets)
            }

            post {
                val principal = call.principal<JWTPrincipal>()
                val studentId = principal?.getClaim("id", String::class) ?: return@post call.respond(HttpStatusCode.Unauthorized)
                
                val request = call.receive<SetBudgetRequest>()
                val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                val month = request.month ?: now.monthNumber
                val year = request.year ?: now.year
                
                val id = budgetService.setBudget(studentId, request.category, BigDecimal.valueOf(request.limitAmount), month, year)
                call.respond(HttpStatusCode.OK, mapOf("id" to id))
            }
        }
    }
}
