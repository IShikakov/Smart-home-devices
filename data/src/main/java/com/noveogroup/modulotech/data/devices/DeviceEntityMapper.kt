package com.noveogroup.modulotech.data.devices

import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.domain.devices.model.Device
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

internal class DeviceEntityMapper {

    fun mapToDomainModel(devices: List<DeviceEntity>): List<Device> =
        devices.map { device -> mapToDomainModel(device) }

    fun mapToDomainModel(device: DeviceEntity): Device = when (device.type) {
        DeviceType.LIGHT -> device.toLight()
        DeviceType.HEATER -> device.toHeater()
        DeviceType.ROLLER_SHUTTER -> device.toRollerShutter()
    }

    private fun DeviceEntity.toLight(): Device.Light = Device.Light(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.OFF,
        intensity = intensity ?: 0
    )

    private fun DeviceEntity.toHeater(): Device.Heater = Device.Heater(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.OFF,
        temperature = temperature ?: 0.0f
    )

    private fun DeviceEntity.toRollerShutter(): Device.RollerShutter = Device.RollerShutter(
        id = id,
        name = deviceName,
        position = position ?: 0
    )

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
}