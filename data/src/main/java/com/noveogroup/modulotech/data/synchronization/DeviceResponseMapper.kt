package com.noveogroup.modulotech.data.synchronization

import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.data.network.response.device.DeviceResponse
import com.noveogroup.modulotech.data.network.response.device.DeviceResponseMode
import com.noveogroup.modulotech.data.network.response.device.DeviceResponseType
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

internal class DeviceResponseMapper {

    fun mapToDatabaseEntity(devices: List<DeviceResponse>): List<DeviceEntity> =
        devices.map { device -> mapToDatabaseEntity(device) }

    private fun mapToDatabaseEntity(device: DeviceResponse): DeviceEntity = with(device) {
        DeviceEntity(
            id = id,
            deviceName = deviceName,
            type = when (productType) {
                DeviceResponseType.Light -> DeviceType.LIGHT
                DeviceResponseType.Heater -> DeviceType.HEATER
                DeviceResponseType.RollerShutter -> DeviceType.ROLLER_SHUTTER
            },
            intensity = intensity,
            mode = mode?.let {
                when (mode) {
                    DeviceResponseMode.ON -> DeviceMode.ON
                    DeviceResponseMode.OFF -> DeviceMode.OFF
                }
            },
            position = position,
            temperature = temperature
        )
    }
}