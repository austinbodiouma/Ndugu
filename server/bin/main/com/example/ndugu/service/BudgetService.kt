package com.example.ndugu.service

import com.example.ndugu.data.BudgetsTable
import com.example.ndugu.data.DatabaseFactory.dbQuery
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.util.*

class BudgetService {

    suspend fun getBudgets(studentId: String, month: Int, year: Int): List<Map<String, Any>> = dbQuery {
        BudgetsTable.selectAll()
            .where { (BudgetsTable.studentId eq studentId) and (BudgetsTable.month eq month) and (BudgetsTable.year eq year) }
            .map { row ->
                mapOf(
                    "id" to row[BudgetsTable.id],
                    "category" to row[BudgetsTable.category],
                    "limitAmount" to row[BudgetsTable.limitAmount].toDouble(),
                    "spentAmount" to row[BudgetsTable.spentAmount].toDouble(),
                    "month" to row[BudgetsTable.month],
                    "year" to row[BudgetsTable.year]
                )
            }
    }

    suspend fun setBudget(studentId: String, category: String, limitAmount: BigDecimal, month: Int, year: Int): String = dbQuery {
        val existing = BudgetsTable.selectAll()
            .where { (BudgetsTable.studentId eq studentId) and (BudgetsTable.category eq category) and (BudgetsTable.month eq month) and (BudgetsTable.year eq year) }
            .singleOrNull()

        if (existing != null) {
            BudgetsTable.update({ BudgetsTable.id eq existing[BudgetsTable.id] }) {
                it[this.limitAmount] = limitAmount
            }
            existing[BudgetsTable.id]
        } else {
            val id = UUID.randomUUID().toString()
            BudgetsTable.insert {
                it[this.id] = id
                it[this.studentId] = studentId
                it[this.category] = category
                it[this.limitAmount] = limitAmount
                it[this.month] = month
                it[this.year] = year
                it[this.createdAt] = Clock.System.now()
            }
            id
        }
    }
}
