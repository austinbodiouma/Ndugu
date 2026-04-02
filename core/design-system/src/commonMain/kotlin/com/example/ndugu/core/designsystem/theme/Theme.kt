package com.example.ndugu.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = CWPrimary,
    onPrimary = CWOnPrimary,
    primaryContainer = CWPrimaryContainer,
    onPrimaryContainer = CWOnPrimaryContainer,
    secondary = CWSecondary,
    onSecondary = CWOnSecondary,
    secondaryContainer = CWSecondaryContainer,
    onSecondaryContainer = CWOnSecondaryContainer,
    tertiary = CWTertiary,
    onTertiary = CWOnTertiary,
    tertiaryContainer = CWTertiaryContainer,
    onTertiaryContainer = CWOnTertiaryContainer,
    error = CWError,
    onError = CWOnError,
    errorContainer = CWErrorContainer,
    onErrorContainer = CWOnErrorContainer,
    background = CWBackground,
    onBackground = CWOnBackground,
    surface = CWSurface,
    onSurface = CWOnSurface,
    surfaceVariant = CWSurfaceVariant,
    onSurfaceVariant = CWOnSurfaceVariant,
    outline = CWOutline,
    outlineVariant = CWOutlineVariant
)

private val DarkColorScheme = darkColorScheme(
    primary = CWInversePrimary,
    onPrimary = CWOnBackground,
    primaryContainer = CWOnPrimaryFixedVariant,
    onPrimaryContainer = CWPrimaryFixed,
    secondary = CWSecondaryFixedDim,
    onSecondary = CWOnSecondaryFixedVariant,
    secondaryContainer = CWOnSecondaryFixedVariant,
    onSecondaryContainer = CWSecondaryFixed,
    tertiary = CWTertiaryFixedDim,
    onTertiary = CWOnTertiaryFixedVariant,
    tertiaryContainer = CWOnTertiaryFixedVariant,
    onTertiaryContainer = CWTertiaryFixed,
    error = CWError80, // Using legacy for dark error for now or generating from base
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
