package com.demo.kaamelott.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Red700,
    primaryVariant = Green600,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Green600,
    onSecondary = Color.White,
    error = Red800,
    onBackground = Color.Black,

)

private val DarkThemeColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200,
    onBackground = Color.White
)
@Composable
fun KaamelottTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) {
            DarkThemeColors
        } else {
            LightThemeColors
        },
        typography = KaamelottTypography,
        shapes = KaamelottShapes,
        content = content
    )
}
