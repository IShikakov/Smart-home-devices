package com.noveogroup.modulotech.ui.theme.darkTheme

import com.noveogroup.modulotech.domain.darkTheme.interactors.DarkThemeInteractor
import kotlinx.coroutines.flow.StateFlow

class DarkThemeDelegate(
    private val darkThemeInteractor: DarkThemeInteractor,
) {
    fun observeDarkThemeState(): StateFlow<Boolean> = darkThemeInteractor.observeDarkThemeState()

    fun toggleDarkTheme(): Unit = darkThemeInteractor.toggleDarkTheme()
}
