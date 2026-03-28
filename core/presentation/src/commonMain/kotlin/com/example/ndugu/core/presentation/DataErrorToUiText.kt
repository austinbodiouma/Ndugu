package com.example.ndugu.core.presentation

import com.example.ndugu.core.domain.util.DataError

/**
 * Maps [DataError] instances to user-friendly [UiText] messages.
 */
fun DataError.toUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.DynamicString("Request timed out. Please try again.")
        DataError.Network.TOO_MANY_REQUESTS -> UiText.DynamicString("Too many requests. Please wait a moment.")
        DataError.Network.NO_INTERNET -> UiText.DynamicString("No internet connection. Please check your network.")
        DataError.Network.SERVER_ERROR -> UiText.DynamicString("Server error. Please try again later.")
        DataError.Network.SERIALIZATION_ERROR -> UiText.DynamicString("Something went wrong. Please try again.")
        DataError.Network.UNAUTHORIZED -> UiText.DynamicString("Session expired. Please log in again.")
        DataError.Network.FORBIDDEN -> UiText.DynamicString("You don't have permission for this action.")
        DataError.Network.NOT_FOUND -> UiText.DynamicString("The requested resource was not found.")
        DataError.Network.CONFLICT -> UiText.DynamicString("A conflict occurred. Please refresh and try again.")
        DataError.Network.UNKNOWN -> UiText.DynamicString("An unknown error occurred. Please try again.")
        DataError.Local.DISK_FULL -> UiText.DynamicString("Storage is full. Please free up space.")
        DataError.Local.NOT_FOUND -> UiText.DynamicString("Data not found locally.")
        DataError.Local.UNKNOWN -> UiText.DynamicString("An unknown local error occurred.")
    }
}
