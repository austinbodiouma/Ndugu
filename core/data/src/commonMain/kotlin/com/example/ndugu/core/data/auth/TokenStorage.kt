package com.example.ndugu.core.data.auth

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import com.russhwolf.settings.get

/**
 * Manages authentication tokens using Multiplatform Settings (key-value storage).
 * Stores access tokens, refresh tokens, and basic user session info.
 */
class TokenStorage(private val settings: Settings) {

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_IS_VERIFIED = "is_verified"
    }

    var accessToken: String?
        get() = settings[KEY_ACCESS_TOKEN]
        set(value) { settings[KEY_ACCESS_TOKEN] = value }

    var refreshToken: String?
        get() = settings[KEY_REFRESH_TOKEN]
        set(value) { settings[KEY_REFRESH_TOKEN] = value }

    var userId: String?
        get() = settings[KEY_USER_ID]
        set(value) { settings[KEY_USER_ID] = value }

    var isLoggedIn: Boolean
        get() = settings[KEY_IS_LOGGED_IN, false]
        set(value) { settings[KEY_IS_LOGGED_IN] = value }

    var isVerified: Boolean
        get() = settings[KEY_IS_VERIFIED, false]
        set(value) { settings[KEY_IS_VERIFIED] = value }

    /**
     * Stores tokens received after successful authentication.
     */
    fun saveAuthTokens(accessToken: String, refreshToken: String, userId: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.userId = userId
        this.isLoggedIn = true
    }

    /**
     * Clears all stored tokens and session data (logout).
     */
    fun clearAll() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
        settings.remove(KEY_USER_ID)
        settings.remove(KEY_IS_LOGGED_IN)
        settings.remove(KEY_IS_VERIFIED)
    }
}
