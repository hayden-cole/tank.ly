package com.demo.tankly.ui.theme

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

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFEB3B), // Bright Yellow
    secondary = Color(0xFFFFC107), // Amber
    background = Color(0xFF000000), // Black
    surface = Color(0xFF212121), // Dark Gray
    onPrimary = Color(0xFF000000), // Black text on yellow
    onSecondary = Color(0xFF000000), // Black text on amber
    onBackground = Color(0xFFB0BEC5), // Light Gray text on black
    onSurface = Color(0xFFB0BEC5), // Light Gray text on dark gray
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFEB3B), // Bright Yellow
    secondary = Color(0xFFFFC107), // Amber
    background = Color(0xFF000000), // Black
    surface = Color(0xFF212121), // Dark Gray
    onPrimary = Color(0xFF000000), // Black text on yellow
    onSecondary = Color(0xFF000000), // Black text on amber
    onBackground = Color(0xFFB0BEC5), // Light Gray text on black
    onSurface = Color(0xFFB0BEC5) // Light Gray text on dark gray
)

@Composable
fun TanklyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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