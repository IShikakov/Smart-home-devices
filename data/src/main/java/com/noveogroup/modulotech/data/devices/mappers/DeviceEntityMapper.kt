package com.noveogroup.modulotech.data.devices.mappers

import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.Heater
import com.noveogroup.modulotech.domain.devices.model.device.Light
import com.noveogroup.modulotech.domain.devices.model.device.RollerShutter

internal object DeviceEntityMapper {

    fun mapToDomainModel(device: DeviceEntity): Device = when (device.type) {
        DeviceType.Light -> device.toLight()
        DeviceType.Heater -> device.toHeater()
        DeviceType.RollerShutter -> device.toRollerShutter()
    }

    fun mapToDatabaseEntity(device: Device): DeviceEntity = with(device) {
        DeviceEntity(
            id = id,
            type = deviceType,
            deviceName = name,
            intensity = if (this is Light) this.intensity else null,
            mode = when (this) {
                is Light -> this.mode
                is Heater -> this.mode
                else -> null
            },
            position = if (this is RollerShutter) this.position else null,
            temperature = if (this is Heater) this.temperature else null,
        )
    }

    private fun DeviceEntity.toLight(): Light = Light(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.Off,
        intensity = intensity ?: 0
    )

    private fun DeviceEntity.toHeater(): Heater = Heater(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.Off,
        temperature = temperature ?: 0.0f
    )

    private fun DeviceEntity.toRollerShutter(): RollerShutter = RollerShutter(
        id = id,
        name = deviceName,
        position = position ?: 0
    )
}