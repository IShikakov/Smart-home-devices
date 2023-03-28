package com.noveogroup.modulotech.domain.darkTheme.interactors

import com.noveogroup.modulotech.domain.darkTheme.api.DarkThemeSaverApi
import kotlinx.coroutines.flow.StateFlow

class DarkThemeInteractor(
    private val darkThemeSaver: DarkThemeSaverApi,
) {
    fun observeDarkThemeState(): StateFlow<Boolean> = darkThemeSaver.observeDarkThemeState()

    fun toggleDarkTheme(): Unit = darkThemeSaver.toggleDarkTheme()
}
