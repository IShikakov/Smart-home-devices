package com.noveogroup.modulotech.data.devices

import com.noveogroup.modulotech.data.devices.entity.DeviceEntity
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.Heater
import com.noveogroup.modulotech.domain.devices.model.device.Light
import com.noveogroup.modulotech.domain.devices.model.device.RollerShutter

internal class DeviceEntityMapper {

    fun mapToDomainModel(devices: List<DeviceEntity>): List<Device> =
        devices.map { device -> mapToDomainModel(device) }

    fun mapToDomainModel(device: DeviceEntity): Device = when (device.type) {
        DeviceType.LIGHT -> device.toLight()
        DeviceType.HEATER -> device.toHeater()
        DeviceType.ROLLER_SHUTTER -> device.toRollerShutter()
    }

    private fun DeviceEntity.toLight(): Light = Light(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.OFF,
        intensity = intensity ?: 0
    )

    private fun DeviceEntity.toHeater(): Heater = Heater(
        id = id,
        name = deviceName,
        mode = mode ?: DeviceMode.OFF,
        temperature = temperature ?: 0.0f
    )

    private fun DeviceEntity.toRollerShutter(): RollerShutter = RollerShutter(
        id = id,
        name = deviceName,
        position = position ?: 0
    )

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
}