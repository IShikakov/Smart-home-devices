package com.noveogroup.modulotech.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SmartHomeAppTheme(
    darkThemeEnabled: Boolean,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkThemeEnabled) darkColorPalette else lightColorPalette
    val systemUiController = rememberSystemUiController()
    if (darkThemeEnabled) {
        systemUiController.setStatusBarColor(color = colorScheme.background)
        systemUiController.setNavigationBarColor(color = colorScheme.background)
    } else {
        systemUiController.setStatusBarColor(color = colorScheme.secondaryVariant)
        systemUiController.setNavigationBarColor(color = colorScheme.background)
    }
    MaterialTheme(
        colors = colorScheme,
        typography = smartHomeTypography,
        content = content,
    )
}
