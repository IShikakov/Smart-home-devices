package com.noveogroup.modulotech.ui.theme.darkTheme

import android.content.Context
import android.content.res.Configuration
import com.noveogroup.modulotech.ui.common.extensions.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DarkThemePreferences(private val context: Context) {

    private val preferences =
        context.getSharedPreferences(DARK_THEME_FILE_NAME, Context.MODE_PRIVATE)
    private val _darkModeFlow: MutableStateFlow<Boolean> = MutableStateFlow(darkThemeEnabled)
    val darkModeFlow: StateFlow<Boolean> = _darkModeFlow

    private var darkThemeEnabled: Boolean
        get() = preferences.getBoolean(DARK_THEME_STATUS_KEY, isSystemInDarkTheme)
        set(value) {
            preferences.edit { putBoolean(DARK_THEME_STATUS_KEY, value) }
            _darkModeFlow.value = value
        }

    fun toggleDarkTheme() {
        darkThemeEnabled = !darkThemeEnabled
    }

    private val isSystemInDarkTheme: Boolean
        get() = context.resources.configuration.uiMode
            .and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

    companion object {
        private const val DARK_THEME_FILE_NAME = "dark_theme_pref"
        private const val DARK_THEME_STATUS_KEY = "dark_theme_enabled_key"
    }
}
