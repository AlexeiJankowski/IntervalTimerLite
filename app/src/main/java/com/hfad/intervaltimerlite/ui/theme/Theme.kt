package com.hfad.intervaltimerlite.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
    primary = Color(0xFF00A896), // Teal
    secondary = Color(0xFFF4A261), // Warm Amber
    tertiary = Color(0xFF0077B6), // Muted Blue

    background = Color(0xFF121212), // Dark background
    surface = Color(0xFF1E1E1E),   // Dark surface for cards, dialogs
    onPrimary = Color.White,       // Contrast for primary
    onSecondary = Color.White,     // Contrast for secondary
    onTertiary = Color.White,      // Contrast for tertiary
    onBackground = Color(0xFFE0E0E0), // Light text on dark background
    onSurface = Color(0xFFE0E0E0)    // Light text on dark surfaces
)

private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
    primary = Color(0xFF00A896), // Teal (same as dark theme for consistency)
    secondary = Color(0xFFF4A261), // Warm Amber
    tertiary = Color(0xFF0077B6), // Muted Blue

    background = Color(0xFFFFFBFE), // Off-white background
    surface = Color(0xFFFFFFFF),    // White surface
    onPrimary = Color(0xFF003C44),  // Darker contrast for teal
    onSecondary = Color(0xFF442C00), // Darker contrast for amber
    onTertiary = Color(0xFF00334C),  // Darker contrast for blue
    onBackground = Color(0xFF1C1B1F), // Dark text on light background
    onSurface = Color(0xFF1C1B1F)    // Dark text on light surfaces
)

@Composable
fun IntervalTimerLiteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}