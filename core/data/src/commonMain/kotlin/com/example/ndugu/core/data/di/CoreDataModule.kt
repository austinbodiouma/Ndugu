package com.example.ndugu.core.data.di

import com.example.ndugu.core.data.auth.TokenStorage
import com.example.ndugu.core.data.networking.createHttpClient
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.auth.providers.BearerTokens
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Core data Koin module — provides HttpClient, TokenStorage, and Settings.
 * Platform-specific modules must provide the Ktor engine factory.
 */
val coreDataModule = module {
    single<Settings> { com.russhwolf.settings.Settings() }
    single { TokenStorage(get()) }
    single<HttpClient> {
        val tokenStorage: TokenStorage = get()
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
                // TODO: Implement token refresh via auth API
                null
            }
        )
    }
}

/**
 * Platform-specific module must provide:
 * - HttpClientEngineFactory (OkHttp for Android, Darwin for iOS)
 */
expect fun platformDataModule(): Module
