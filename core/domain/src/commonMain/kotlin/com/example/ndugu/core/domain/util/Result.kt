package com.example.ndugu.core.domain.util

/**
 * A discriminated union that encapsulates a successful outcome with a value of type [D]
 * or a failure with an error of type [E].
 */
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.example.ndugu.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

/**
 * Marker interface for all error types in the application.
 */
interface Error

/**
 * A Result that carries no data on success — only indicates success or failure.
 */
typealias EmptyResult<E> = Result<Unit, E>

/**
 * Maps the success value of a [Result] to a new value using the provided [map] function.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
    }
}

/**
 * Converts a [Result] with data into an [EmptyResult], discarding the success value.
 */
fun <T, E : Error> Result<T, E>.asEmptyResult(): EmptyResult<E> {
    return map { }
}

/**
 * Executes the given [action] if this [Result] is a [Result.Success].
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

/**
 * Executes the given [action] if this [Result] is a [Result.Error].
 */
inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) {
        action(error)
    }
    return this
}
