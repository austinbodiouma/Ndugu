package com.example.ndugu.core.data.networking

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Creates a configured Ktor HttpClient for CampusWallet API calls.
 *
 * @param engine Platform-specific Ktor engine (OkHttp for Android, Darwin for iOS)
 * @param tokenProvider Lambda that returns current access/refresh tokens, or null if not authenticated
 * @param tokenRefresher Lambda that refreshes expired tokens and returns new ones
 */
fun createHttpClient(
    engine: io.ktor.client.engine.HttpClientEngineFactory<*>,
    baseUrl: String = "http://10.0.2.2:8080", // Android emulator localhost
    tokenProvider: (suspend () -> BearerTokens?)? = null,
    tokenRefresher: (suspend () -> BearerTokens?)? = null
): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.BODY
        }

        if (tokenProvider != null) {
            install(Auth) {
                bearer {
                    loadTokens {
                        tokenProvider()
                    }
                    refreshTokens {
                        tokenRefresher?.invoke()
                    }
                }
            }
        }

        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
        }
    }
}
