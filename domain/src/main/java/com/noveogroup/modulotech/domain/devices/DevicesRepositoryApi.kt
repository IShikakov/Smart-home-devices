package com.noveogroup.modulotech.domain.devices

import com.noveogroup.modulotech.domain.devices.model.Device
import kotlinx.coroutines.flow.Flow

interface DevicesRepositoryApi {
    fun observeDevices(): Flow<List<Device>>
    suspend fun deleteDevice(id: String)
}