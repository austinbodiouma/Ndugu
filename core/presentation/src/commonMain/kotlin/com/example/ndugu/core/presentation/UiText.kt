package com.example.ndugu.core.presentation

/**
 * Represents a text value that can either be a dynamic string or a string resource reference.
 * Used in ViewModels and UI state to handle both localized and runtime-generated strings.
 */
sealed interface UiText {

    /**
     * A string value determined at runtime (e.g., user's name, formatted currency).
     */
    data class DynamicString(val value: String) : UiText

    /**
     * A reference to a string resource (for localization support).
     * In Compose Multiplatform, this maps to compose resources.
     *
     * @param id The string resource identifier
     * @param args Format arguments for the string resource
     */
    class StringResourceText(
        val id: String,
        val args: Array<Any> = emptyArray()
    ) : UiText {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is StringResourceText) return false
            return id == other.id && args.contentEquals(other.args)
        }

        override fun hashCode(): Int {
            var result = id.hashCode()
            result = 31 * result + args.contentHashCode()
            return result
        }
    }
}

/**
 * Converts a [UiText] to a plain string for display.
 * For StringResourceText, falls back to the ID string — actual resource resolution
 * should be done in the Compose layer.
 */
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringResourceText -> id // TODO: resolve via compose resources
    }
}
