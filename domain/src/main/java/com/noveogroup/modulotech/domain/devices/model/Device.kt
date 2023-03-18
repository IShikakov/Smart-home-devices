package com.noveogroup.modulotech.domain.devices.model

sealed interface Device {
    val id: Long
    val name: String
    val deviceType: DeviceType

    data class Light(
        override val id: Long,
        override val name: String,
        val isOn: Boolean,
        val intensity: Int,
    ) : Device {
        override val deviceType: DeviceType = DeviceType.LIGHT
    }

    data class Heater(
        override val id: Long,
        override val name: String,
        val isOn: Boolean,
        val temperature: Float,
    ) : Device {
        override val deviceType: DeviceType = DeviceType.HEATER
    }

    data class RollerShutter(
        override val id: Long,
        override val name: String,
        val position: Int,
    ) : Device {
        override val deviceType: DeviceType = DeviceType.ROLLER_SHUTTER

    }
}
