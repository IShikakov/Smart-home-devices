package com.noveogroup.modulotech.di

import com.noveogroup.modulotech.domain.devices.DevicesListInteractor
import com.noveogroup.modulotech.domain.devices.details.DeviceDetailsInteractor
import com.noveogroup.modulotech.domain.synchronization.DataSyncInteractor
import com.noveogroup.modulotech.domain.user.UserProfileInteractor
import com.noveogroup.modulotech.ui.devices.details.DeviceDetailsViewModel
import com.noveogroup.modulotech.ui.devices.list.DevicesListViewModel
import com.noveogroup.modulotech.ui.devices.list.common.DevicesListMapper
import com.noveogroup.modulotech.ui.profile.UserProfileViewModel
import com.noveogroup.modulotech.ui.profile.common.UserProfileMapper
import com.noveogroup.modulotech.ui.profile.validation.UserProfileValidator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val devicesListModule = module {
    viewModelOf(::DevicesListViewModel)
    factoryOf(::DataSyncInteractor)
    factoryOf(::DevicesListInteractor)
    factoryOf(::DevicesListMapper)
}

val deviceDetailsModule = module {
    viewModelOf(::DeviceDetailsViewModel)
    factoryOf(::DeviceDetailsInteractor)
}

val userProfileModule = module {
    viewModelOf(::UserProfileViewModel)
    factoryOf(::UserProfileInteractor)
    factoryOf(::UserProfileValidator)
    factoryOf(::UserProfileMapper)
}