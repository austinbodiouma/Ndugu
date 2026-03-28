package com.example.ndugu.core.data.networking

import com.example.ndugu.core.domain.util.DataError
import com.example.ndugu.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

/**
 * Executes a GET request and wraps the response in a [Result].
 */
suspend inline fun <reified T> HttpClient.safeGet(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T, DataError.Network> {
    return safeCall { get(urlString, block) }
}

/**
 * Executes a POST request and wraps the response in a [Result].
 */
suspend inline fun <reified T> HttpClient.safePost(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T, DataError.Network> {
    return safeCall { post(urlString, block) }
}

/**
 * Executes a PUT request and wraps the response in a [Result].
 */
suspend inline fun <reified T> HttpClient.safePut(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T, DataError.Network> {
    return safeCall { put(urlString, block) }
}

/**
 * Executes a DELETE request and wraps the response in a [Result].
 */
suspend inline fun <reified T> HttpClient.safeDelete(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T, DataError.Network> {
    return safeCall { delete(urlString, block) }
}

/**
 * Wraps a Ktor HTTP call with comprehensive error handling,
 * mapping HTTP status codes and exceptions to [DataError.Network].
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Network> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(DataError.Network.SERIALIZATION_ERROR)
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

/**
 * Maps an HTTP response to a [Result] based on status code.
 */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        403 -> Result.Error(DataError.Network.FORBIDDEN)
        404 -> Result.Error(DataError.Network.NOT_FOUND)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}
