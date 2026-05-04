package com.example.ndugu.data

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

object DatabaseFactory {
    fun init() {
        val databaseUrl = System.getenv("DATABASE_URL")
        val dbHost = System.getenv("DB_HOST")
        val dbPort = System.getenv("DB_PORT")
        val dbName = System.getenv("DB_NAME")
        val dbUser = System.getenv("DB_USER")
        val dbPass = System.getenv("DB_PASS")

        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            
            when {
                databaseUrl != null -> {
                    println("Initializing database using DATABASE_URL")
                    val uri = URI(databaseUrl.replace("postgres://", "postgresql://"))
                    val host = uri.host
                    val port = if (uri.port != -1) uri.port else 5432
                    val dbPath = uri.path.removePrefix("/")
                    val query = uri.rawQuery?.let { "?$it" } ?: ""

                    jdbcUrl = "jdbc:postgresql://$host:$port/$dbPath$query"
                    uri.userInfo?.let { userInfo ->
                        val parts = userInfo.split(":")
                        username = parts[0]
                        if (parts.size > 1) password = parts[1]
                    }
                }
                dbHost != null -> {
                    println("Initializing database using individual DB_* variables")
                    val port = dbPort ?: "5432"
                    val name = dbName ?: "ndugu_db"
                    jdbcUrl = "jdbc:postgresql://$dbHost:$port/$name"
                    username = dbUser
                    password = dbPass
                }
                else -> {
                    println("Initializing database using local defaults (127.0.0.1)")
                    jdbcUrl = "jdbc:postgresql://127.0.0.1:5444/ndugu_db"
                    username = dbUser ?: "ndugu_user"
                    password = dbPass ?: "ndugu_password"
                }
            }

            maximumPoolSize = 2
            minimumIdle = 1
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            connectionTimeout = 10000
            idleTimeout = 300000
            maxLifetime = 600000
            validate()
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(
                StudentsTable,
                RefreshTokensTable,
                WalletsTable,
                TransactionsTable,
                ReversalRequestsTable,
                BudgetsTable,
                ListingsTable,
                OrdersTable,
                DisputesTable,
                RatingsTable,
                ConversationsTable,
                ConversationParticipantsTable,
                MessagesTable,
                PartnerBusinessesTable,
                QRPaymentsTable
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction { block() }
}
