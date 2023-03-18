package com.noveogroup.modulotech.di

import com.noveogroup.modulotech.domain.devices.DevicesInteractor
import com.noveogroup.modulotech.ui.devices.list.DevicesListViewModel
import com.noveogroup.modulotech.ui.devices.list.common.DevicesListMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val devicesListModule = module {
    viewModelOf(::DevicesListViewModel)
    factoryOf(::DevicesInteractor)
    factoryOf(::DevicesListMapper)
}