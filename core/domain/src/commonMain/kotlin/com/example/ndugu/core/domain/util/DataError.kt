package com.example.ndugu.core.domain.util

/**
 * Sealed hierarchy of data-related errors that can occur across the application.
 * All errors implement the [Error] marker interface for use with [Result].
 */
sealed interface DataError : Error {

    /**
     * Errors that occur during network operations (API calls, webhook processing, etc.)
     */
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION_ERROR,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        CONFLICT,
        UNKNOWN
    }

    /**
     * Errors that occur during local data operations (Room, Settings, file I/O, etc.)
     */
    enum class Local : DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}
