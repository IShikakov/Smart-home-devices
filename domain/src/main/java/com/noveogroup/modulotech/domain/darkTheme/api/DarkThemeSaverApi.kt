package com.noveogroup.modulotech.domain.darkTheme.api

import kotlinx.coroutines.flow.StateFlow

interface DarkThemeSaverApi {
    fun observeDarkThemeState(): StateFlow<Boolean>
    fun toggleDarkTheme()
}
