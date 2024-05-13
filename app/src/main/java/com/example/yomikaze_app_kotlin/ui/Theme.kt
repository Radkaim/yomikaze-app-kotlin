package com.example.yomikaze_app_kotlin.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    //
    primary = Red500,
    onPrimary = Green600,
    onPrimaryContainer = Red400,
    secondary = Green900,
    onSecondary = Red900,
    onSecondaryContainer = Green200,
    //font
    tertiary = Green900,
    onTertiary = Red900,
    onTertiaryContainer = white,
    //
    outline = Green400,
    outlineVariant = Red600,

    background = Green100,
    onBackground = white

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
        AppTheme.Default -> {
            if (isDarkMode) {
                DarkColorScheme
            } else {
                LightColorScheme
            }
        }
        AppTheme.Light -> {
            LightColorScheme
        }
        AppTheme.Dark -> {
            DarkColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}