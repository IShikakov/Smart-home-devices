package com.noveogroup.modulotech.data.di

import com.noveogroup.modulotech.data.devices.DeviceEntityMapper
import com.noveogroup.modulotech.data.devices.DevicesRepository
import com.noveogroup.modulotech.data.synchronization.DataSyncRepository
import com.noveogroup.modulotech.data.synchronization.DeviceResponseMapper
import com.noveogroup.modulotech.data.synchronization.UserResponseMapper
import com.noveogroup.modulotech.domain.devices.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.synchronization.DataSyncRepositoryApi
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val synchronizationModule = module {
    singleOf(::DataSyncRepository) bind DataSyncRepositoryApi::class
    factoryOf(::UserResponseMapper)
    factoryOf(::DeviceResponseMapper)
}

val devicesModule = module {
    singleOf(::DevicesRepository) bind DevicesRepositoryApi::class
    factoryOf(::DeviceEntityMapper)
}