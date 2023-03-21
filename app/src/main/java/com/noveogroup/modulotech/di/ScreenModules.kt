package com.noveogroup.modulotech.di

import com.noveogroup.modulotech.domain.devices.DevicesListInteractor
import com.noveogroup.modulotech.domain.synchronization.DataSyncInteractor
import com.noveogroup.modulotech.domain.user.UserProfileInteractor
import com.noveogroup.modulotech.ui.devices.list.DevicesListViewModel
import com.noveogroup.modulotech.ui.devices.list.common.DevicesListMapper
import com.noveogroup.modulotech.ui.profile.UserProfileViewModel
import com.noveogroup.modulotech.ui.profile.common.UserProfileMapper
import com.noveogroup.modulotech.ui.profile.validation.UserProfileValidator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val devicesListModule = module {
    viewModelOf(::DevicesListViewModel)
    singleOf(::DataSyncInteractor)
    singleOf(::DevicesListInteractor)
    factoryOf(::DevicesListMapper)
}

val userProfileModule = module {
    viewModelOf(::UserProfileViewModel)
    singleOf(::UserProfileInteractor)
    factoryOf(::UserProfileValidator)
    factoryOf(::UserProfileMapper)
}