package com.noveogroup.modulotech.ui.theme.darkTheme

import kotlinx.coroutines.flow.StateFlow

class DarkThemeDelegate(private val darkThemePreferences: DarkThemePreferences) {

    fun observeDarkThemeState(): StateFlow<Boolean> = darkThemePreferences.darkModeFlow

    fun toggleDarkTheme(): Unit = darkThemePreferences.toggleDarkTheme()
}
