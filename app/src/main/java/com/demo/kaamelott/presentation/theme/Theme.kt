package com.demo.kaamelott.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    onPrimary = Color.White,
    secondary = Purple200,
    secondaryVariant = Purple700,
    onSecondary = Color.White,
    error = Red800,
    onBackground = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    onPrimary = Color.Black,
    secondary = Purple500,
    onSecondary = Color.Black,
    error = Red200,
    onBackground = Color.White

)

@Composable
fun KaamelottTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) {
            DarkColorPalette
        } else {
            LightColorPalette
        },
        typography = KaamelottTypography,
        shapes = KaamelottShapes,
        content = content
    )
}
