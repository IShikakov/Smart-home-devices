package com.noveogroup.modulotech.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)
val lightGray = Color(0xFFCCCCCC)
val darkGray = Color(0xFF121212)
val lightRed = Color(0xFFFC6679)
val darkRed = Color(0xFFB00020)
val black = Color(0xFF000000)
val white = Color(0xFFFFFFFF)

val darkColorPalette = darkColors(
    primary = purple200,
    primaryVariant = purple700,
    secondary = purple200,
    secondaryVariant = purple200,
    background = darkGray,
    surface = darkGray,
    error = lightRed,
    onPrimary = black,
    onSecondary = black,
    onBackground = white,
    onSurface = white,
    onError = black,
)

val lightColorPalette = lightColors(
    primary = purple500,
    primaryVariant = purple700,
    secondary = purple500,
    secondaryVariant = purple500,
    background = white,
    surface = white,
    error = darkRed,
    onPrimary = white,
    onSecondary = black,
    onBackground = black,
    onSurface = black,
    onError = white,
)
