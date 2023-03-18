package com.noveogroup.modulotech.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SmartHomeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) darkColorPalette else lightColorPalette
    MaterialTheme(
        colors = colorScheme,
        typography = smartHomeTypography,
        content = content
    )
}