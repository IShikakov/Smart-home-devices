package com.noveogroup.modulotech.data.settings.darkTheme

import android.content.Context
import android.content.res.Configuration
import com.noveogroup.modulotech.data.common.extensions.edit
import com.noveogroup.modulotech.domain.darkTheme.api.DarkThemeSaverApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DarkThemePreferences(private val context: Context) : DarkThemeSaverApi {

    private val preferences =
        context.getSharedPreferences(DARK_THEME_FILE_NAME, Context.MODE_PRIVATE)
    private val _darkModeFlow: MutableStateFlow<Boolean> = MutableStateFlow(darkThemeEnabled)
    private var darkThemeEnabled: Boolean
        get() = preferences.getBoolean(DARK_THEME_STATUS_KEY, isSystemInDarkTheme)
        set(value) {
            preferences.edit { putBoolean(DARK_THEME_STATUS_KEY, value) }
            _darkModeFlow.value = value
        }

    override fun observeDarkThemeState(): StateFlow<Boolean> = _darkModeFlow

    override fun toggleDarkTheme() {
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
