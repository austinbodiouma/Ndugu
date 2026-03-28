package com.example.ndugu.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a [Flow] of one-time events in a lifecycle-aware manner.
 * Events are only processed when the lifecycle is at least STARTED.
 *
 * Usage:
 * ```
 * ObserveAsEvents(viewModel.events) { event ->
 *     when (event) {
 *         is MyEvent.Navigate -> navigator.navigate(event.route)
 *         is MyEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
 *     }
 * }
 * ```
 */
@Composable
fun <T> ObserveAsEvents(
    events: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                events.collect(onEvent)
            }
        }
    }
}
