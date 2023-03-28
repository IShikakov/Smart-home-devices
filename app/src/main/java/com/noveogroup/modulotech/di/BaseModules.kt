package com.noveogroup.modulotech.di

import com.noveogroup.modulotech.data.settings.darkTheme.DarkThemePreferences
import com.noveogroup.modulotech.domain.darkTheme.api.DarkThemeSaverApi
import com.noveogroup.modulotech.domain.darkTheme.interactors.DarkThemeInteractor
import com.noveogroup.modulotech.ui.common.resources.ResourcesManager
import com.noveogroup.modulotech.ui.theme.darkTheme.DarkThemeDelegate
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val resourcesModule = module {
    singleOf(::ResourcesManager)
}

val darkThemeModule = module {
    singleOf(::DarkThemeDelegate)
    singleOf(::DarkThemeInteractor)
    singleOf(::DarkThemePreferences) bind DarkThemeSaverApi::class
}
