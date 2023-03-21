package com.noveogroup.modulotech.domain.devices.model

sealed interface Device {
    val id: String
    val name: String
    val deviceType: DeviceType

    data class Light(
        override val id: String,
        override val name: String,
        val mode: DeviceMode,
        val intensity: Int,
    ) : Device {
        override val deviceType: DeviceType = DeviceType.LIGHT
    }

    data class Heater(
        override val id: String,
        override val name: String,
        val mode: DeviceMode,
        val temperature: Float,
    ) : Device {
        override val deviceType: DeviceType = DeviceType.HEATER
    }

    data class RollerShutter(
        override val id: String,
        override val name: String,
        val position: Int,
    ) : Device {
        override val deviceType: DeviceType = DeviceType.ROLLER_SHUTTER

    }
}
