package com.noveogroup.modulotech.domain.devices.model.device

import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

data class Heater(
    override val id: String,
    override val name: String,
    val mode: DeviceMode,
    val temperature: Float,
) : Device {
    override val deviceType: DeviceType = DeviceType.Heater
}
