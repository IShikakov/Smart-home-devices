package com.noveogroup.modulotech.di

import com.noveogroup.modulotech.ui.common.resources.ResourcesManager
import com.noveogroup.modulotech.ui.theme.darkTheme.DarkThemeDelegate
import com.noveogroup.modulotech.ui.theme.darkTheme.DarkThemePreferences
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val resourcesModule = module {
    singleOf(::ResourcesManager)
}

val darkThemeModule = module {
    singleOf(::DarkThemeDelegate)
    singleOf(::DarkThemePreferences)
}
