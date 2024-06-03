package com.example.yomikaze_app_kotlin.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Green300,
    onPrimary = Green400,
    secondary = Red300,
    onSecondary = Red400,
)

private val LightColorScheme = lightColorScheme(
    primary = Red300,
    onPrimary = Red400,
    secondary = Green300,
    onSecondary = Green400,

    tertiary = Color.White



    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun YomikazeappkotlinTheme(
    appTheme: AppTheme,
    isDarkMode: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when (appTheme) {
        AppTheme.DEFAULT -> {
            if (isDarkMode) {
                DarkColorScheme
            } else {
                LightColorScheme
            }
        }
        AppTheme.LIGHT -> {
            LightColorScheme
        }
        AppTheme.DARK -> {
            DarkColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}