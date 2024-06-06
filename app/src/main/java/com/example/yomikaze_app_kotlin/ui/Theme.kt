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
    secondary = Orange300,
    onSecondary = Orange400,
    tertiary = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Green500,
    onPrimary = Orange500,
    primaryContainer = Green900,
    secondary = Green300,
    onSecondary = Green400,

    secondaryContainer = Green500,


    background = Green100,
    onBackground = GrayA07,
    tertiary = Color.White,

    outline = OrangeSplash,
    outlineVariant = GreenSplash,

    surface = Orange400
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