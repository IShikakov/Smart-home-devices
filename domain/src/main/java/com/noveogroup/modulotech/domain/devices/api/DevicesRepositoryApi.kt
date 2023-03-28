package com.noveogroup.modulotech.domain.devices.api

import com.noveogroup.modulotech.domain.devices.model.device.Device
import kotlinx.coroutines.flow.Flow

interface DevicesRepositoryApi {
    fun observeDevices(): Flow<List<Device>>
    suspend fun getDeviceById(id: String): Device
    suspend fun updateDevice(device: Device)
    suspend fun deleteDevice(id: String)
}
