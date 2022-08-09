package com.smartflowtech.cupidcustomerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = darkBlue,
    primaryVariant = smartFlowBlue,
    secondary = grey,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = darkBlue,
    onBackground = darkBlue,
    onSurface = darkBlue,
)

private val LightColorPalette = lightColors(
    primary = darkBlue,
    primaryVariant = smartFlowBlue,
    secondary = grey,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = darkBlue,
    onBackground = darkBlue,
    onSurface = darkBlue,
)

@Composable
fun CupidCustomerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}