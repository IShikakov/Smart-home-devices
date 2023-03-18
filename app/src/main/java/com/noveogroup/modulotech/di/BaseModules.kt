package com.noveogroup.modulotech.di

import com.noveogroup.modulotech.ui.common.ResourcesManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val resourcesModule = module {
    singleOf(::ResourcesManager)
}