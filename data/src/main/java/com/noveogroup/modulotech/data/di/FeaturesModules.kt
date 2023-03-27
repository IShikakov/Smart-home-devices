package com.noveogroup.modulotech.data.di

import com.noveogroup.modulotech.data.devices.DevicesRepository
import com.noveogroup.modulotech.data.synchronization.DataSyncRepository
import com.noveogroup.modulotech.data.user.UserRepository
import com.noveogroup.modulotech.domain.devices.api.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.synchronization.api.DataSyncRepositoryApi
import com.noveogroup.modulotech.domain.user.api.UserRepositoryApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val synchronizationModule = module {
    singleOf(::DataSyncRepository) bind DataSyncRepositoryApi::class
}

val devicesModule = module {
    singleOf(::DevicesRepository) bind DevicesRepositoryApi::class
}

val userModule = module {
    singleOf(::UserRepository) bind UserRepositoryApi::class
}