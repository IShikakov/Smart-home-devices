package com.noveogroup.modulotech.domain.devices.model.device

sealed class Device(
    val id: String,
    val name: String,
    val deviceType: DeviceType,
) {
    class Light(
        id: String,
        name: String,
        val mode: DeviceMode,
        val intensity: Int,
    ) : Device(id, name, DeviceType.Light)

    class Heater(
        id: String,
        name: String,
        val mode: DeviceMode,
        val temperature: Float,
    ) : Device(id, name, DeviceType.Heater)

    class RollerShutter(
        id: String,
        name: String,
        val position: Int,
    ) : Device(id, name, DeviceType.RollerShutter)
}
