package com.noveogroup.modulotech.data.devices.mappers

import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.device.DeviceType

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
            intensity = if (this is Device.Light) this.intensity else null,
            mode = when (this) {
                is Device.Light -> this.mode
                is Device.Heater -> this.mode
                else -> null
            },
            position = if (this is Device.RollerShutter) this.position else null,
            temperature = if (this is Device.Heater) this.temperature else null,
        )
    }

    private fun DeviceEntity.toLight(): Device.Light = Device.Light(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.Off,
        intensity = intensity ?: 0,
    )

    private fun DeviceEntity.toHeater(): Device.Heater = Device.Heater(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.Off,
        temperature = temperature ?: 0.0f,
    )

    private fun DeviceEntity.toRollerShutter(): Device.RollerShutter = Device.RollerShutter(
        id = id,
        name = deviceName,
        position = position ?: 0,
    )
}
