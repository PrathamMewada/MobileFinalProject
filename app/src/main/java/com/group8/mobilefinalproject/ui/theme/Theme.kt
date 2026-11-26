// Keep the package line from your existing Theme.kt file
package com.group8.mobilefinalproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light mode colors
private val LightColorScheme = lightColorScheme(
    primary = VagabondBlue,
    onPrimary = Color.White,
    primaryContainer = SkyTint,
    onPrimaryContainer = VagabondBlue,
    secondary = SunsetOrange,
    onSecondary = Color.White,
    tertiary = BudgetUnder,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = VagabondNavy,
    surface = Color.White,
    onSurface = VagabondNavy,
    surfaceVariant = SkyTint,
    onSurfaceVariant = SlateGray,
    outline = LineGray,
    error = BudgetOver,
    onError = Color.White
)

// Dark mode colors
private val DarkColorScheme = darkColorScheme(
    primary = BlueDark,
    onPrimary = VagabondNavy,
    primaryContainer = DarkSurfaceVariant,
    onPrimaryContainer = BlueDark,
    secondary = OrangeDark,
    onSecondary = VagabondNavy,
    tertiary = BudgetUnder,
    onTertiary = VagabondNavy,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkSurface,
    onSurface = DarkText,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkSecondaryText,
    outline = DarkSurfaceVariant,
    error = BudgetOver,
    onError = Color.White
)

/**
 * App theme for Vagabond.
 * Uses the light or dark color scheme based on the device setting.
 * Dynamic color is not used so the brand colors stay the same on every device.
 */
@Composable
fun VagabondTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
