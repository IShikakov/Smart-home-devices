package com.noveogroup.modulotech.domain.devices.details

import com.noveogroup.modulotech.domain.devices.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.model.Device

class DeviceDetailsInteractor(
    private val devicesRepository: DevicesRepositoryApi,
) {

    suspend fun getDeviceById(id: String): Device = devicesRepository.getDeviceById(id)

    suspend fun updateDevice(device: Device): Unit = devicesRepository.updateDevice(device)
}