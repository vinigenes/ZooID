package com.example.zooid.ui.activities

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF00695C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2DFDB),
    onPrimaryContainer = Color(0xFF004D40),
    secondary = Color(0xFF4DB6AC),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB2DFDB),
    onSecondaryContainer = Color(0xFF004D40),
    background = Color(0xFFF1F8F7),
    onBackground = Color(0xFF00251A),
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF4DB6AC),
    onPrimary = Color(0xFF00332B),
    primaryContainer = Color(0xFF004D40),
    onPrimaryContainer = Color(0xFFB2DFDB),
    secondary = Color(0xFF80CBC4),
    onSecondary = Color(0xFF00332B),
    secondaryContainer = Color(0xFF004D40),
    onSecondaryContainer = Color(0xFFB2DFDB),
    background = Color(0xFF00251A),
    onBackground = Color(0xFFB2DFDB),
    surface = Color(0xFF00332B),
    onSurface = Color(0xFFE0F2F1),
    error = Color(0xFFCF6679),
    onError = Color(0xFF370617),
)

val AppTypography = Typography()

@Composable
fun ZooIDTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}
