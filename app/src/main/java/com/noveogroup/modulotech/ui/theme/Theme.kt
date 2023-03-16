package com.noveogroup.modulotech.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = teal200,
    secondaryVariant = teal200,
    background = darkGray,
    surface = darkGray,
    error = lightRed,
    onPrimary = black,
    onSecondary = black,
    onBackground = white,
    onSurface = white,
    onError = black
)

private val LightColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = teal200,
    secondaryVariant = teal900,
    background = white,
    surface = white,
    error = darkRed,
    onPrimary = white,
    onSecondary = black,
    onBackground = black,
    onSurface = black,
    onError = white
)

@Composable
fun SmartHomeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(
        colors = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}