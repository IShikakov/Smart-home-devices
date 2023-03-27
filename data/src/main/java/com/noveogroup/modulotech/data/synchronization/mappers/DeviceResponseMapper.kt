package com.noveogroup.modulotech.data.synchronization.mappers

import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.data.network.response.device.DeviceResponse
import com.noveogroup.modulotech.data.network.response.device.DeviceResponseMode
import com.noveogroup.modulotech.data.network.response.device.DeviceResponseType
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

internal object DeviceResponseMapper {

    fun mapToDatabaseEntity(device: DeviceResponse): DeviceEntity? = with(device) {
        if (id == null || productType == null) return@with null
        DeviceEntity(
            id = id,
            deviceName = deviceName ?: "",
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