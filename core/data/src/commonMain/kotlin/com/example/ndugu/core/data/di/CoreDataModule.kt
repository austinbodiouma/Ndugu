package com.example.ndugu.core.data.di

import com.example.ndugu.core.data.auth.TokenStorage
import com.example.ndugu.core.data.networking.createHttpClient
import com.example.ndugu.feature.auth.data.remote.dto.AuthResponseDto
import com.example.ndugu.feature.auth.data.remote.dto.RefreshRequest
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Core data Koin module — provides HttpClient, TokenStorage, and Settings.
 * Platform-specific modules must provide the Ktor engine factory.
 */
val coreDataModule = module {
    single<Settings> { com.russhwolf.settings.Settings() }
    single { TokenStorage(get()) }
    single(named("baseHttpClient")) {
        createHttpClient(
            engine = get<HttpClientEngineFactory<*>>(),
            tokenProvider = null
        )
    }
    single<HttpClient> {
        val tokenStorage: TokenStorage = get()
        val baseHttpClient: HttpClient = get(named("baseHttpClient"))
        
        createHttpClient(
            engine = get<HttpClientEngineFactory<*>>(),
            tokenProvider = {
                val access = tokenStorage.accessToken
                val refresh = tokenStorage.refreshToken
                if (access != null && refresh != null) {
                    BearerTokens(access, refresh)
                } else {
                    null
                }
            },
            tokenRefresher = {
                val refreshToken = tokenStorage.refreshToken ?: return@createHttpClient null
                try {
                    val response = baseHttpClient.post("/api/auth/refresh") {
                        setBody(RefreshRequest(refreshToken))
                    }.body<AuthResponseDto>()
                    
                    tokenStorage.saveAuthTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken,
                        userId = response.studentId
                    )
                    
                    BearerTokens(response.accessToken, response.refreshToken)
                } catch (e: Exception) {
                    tokenStorage.clearAll()
                    null
                }
            }
        )
    }
}

/**
 * Platform-specific module must provide:
 * - HttpClientEngineFactory (OkHttp for Android, Darwin for iOS)
 */
expect fun platformDataModule(): Module
