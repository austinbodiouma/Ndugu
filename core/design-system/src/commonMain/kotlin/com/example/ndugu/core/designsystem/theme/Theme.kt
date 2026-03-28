package com.example.ndugu.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CWGreen40,
    onPrimary = CWNeutral99,
    primaryContainer = CWGreen90,
    onPrimaryContainer = CWGreen10,
    secondary = CWAmber40,
    onSecondary = CWNeutral99,
    secondaryContainer = CWAmber90,
    onSecondaryContainer = CWAmber10,
    tertiary = CWTeal40,
    onTertiary = CWNeutral99,
    tertiaryContainer = CWTeal90,
    onTertiaryContainer = CWTeal10,
    error = CWError40,
    onError = CWNeutral99,
    errorContainer = CWError90,
    onErrorContainer = CWError10,
    background = CWNeutral99,
    onBackground = CWNeutral10,
    surface = CWNeutral99,
    onSurface = CWNeutral10,
    surfaceVariant = CWNeutralVariant90,
    onSurfaceVariant = CWNeutralVariant30,
    outline = CWNeutralVariant50,
    outlineVariant = CWNeutralVariant80
)

private val DarkColorScheme = darkColorScheme(
    primary = CWGreen80,
    onPrimary = CWGreen20,
    primaryContainer = CWGreen30,
    onPrimaryContainer = CWGreen90,
    secondary = CWAmber80,
    onSecondary = CWAmber20,
    secondaryContainer = CWAmber30,
    onSecondaryContainer = CWAmber90,
    tertiary = CWTeal80,
    onTertiary = CWTeal20,
    tertiaryContainer = CWTeal30,
    onTertiaryContainer = CWTeal90,
    error = CWError80,
    onError = CWError20,
    errorContainer = CWError30,
    onErrorContainer = CWError90,
    background = CWNeutral10,
    onBackground = CWNeutral90,
    surface = CWNeutral10,
    onSurface = CWNeutral90,
    surfaceVariant = CWNeutralVariant30,
    onSurfaceVariant = CWNeutralVariant80,
    outline = CWNeutralVariant60,
    outlineVariant = CWNeutralVariant30
)

/**
 * CampusWallet Material 3 theme with light/dark color scheme support.
 *
 * Usage:
 * ```
 * CampusWalletTheme {
 *     // Your composable content
 * }
 * ```
 */
@Composable
fun CampusWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CampusWalletTypography,
        content = content
    )
}
