package com.example.yomikaze_app_kotlin.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Green300,
    onPrimary = Green500,
    secondary = Orange300,
    onSecondary = Orange400,
    tertiary = Color.White,


)

private val LightColorScheme = lightColorScheme(
    primary = Green500, //1FC488
    onPrimary = Orange500, //E05C48
    primaryContainer = Green900, //00260F
    inversePrimary = Green700, //02753E

    secondary = Green300, //89F8DC
    onSecondary = Green500, //1FC488 -> Green500
    secondaryContainer = Green600, //0B9D5F -> Green600
    onSecondaryContainer = Orange900, //02753E

    error = Orange600, //B22F24
    onError = Orange700, //831513

    background = Green100, //F2FFFD
    onBackground = GrayA07, //D9D9D9

    surface = Orange400, //F28C74
    onSurface = Orange100, //FFF7F2
    inverseSurface = Orange800, //550A0A
    inverseOnSurface = switchTrackColor,
    surfaceTint = Green800,


    tertiary = Color.White, //FFFFFF
    onTertiary = Color.Black,
    tertiaryContainer = Green200, //BFFEF2


    outline = OrangeSplash, //E38B6C
    outlineVariant = GreenSplash, //65F6D1

    scrim = coinColor

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