package com.noveogroup.modulotech.domain.devices.interactors

import com.noveogroup.modulotech.domain.devices.api.DevicesRepositoryApi
import com.noveogroup.modulotech.domain.devices.model.device.Device

class DeviceDetailsInteractor(
    private val devicesRepository: DevicesRepositoryApi,
) {

    suspend fun getDeviceById(id: String): Device = devicesRepository.getDeviceById(id)

    suspend fun updateDevice(device: Device): Unit = devicesRepository.updateDevice(device)
}
