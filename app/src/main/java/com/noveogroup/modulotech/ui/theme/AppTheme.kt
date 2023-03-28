package com.noveogroup.modulotech.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SmartHomeAppTheme(
    darkThemeEnabled: Boolean,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkThemeEnabled) darkColorPalette else lightColorPalette
    MaterialTheme(
        colors = colorScheme,
        typography = smartHomeTypography,
        content = content,
    )
}
