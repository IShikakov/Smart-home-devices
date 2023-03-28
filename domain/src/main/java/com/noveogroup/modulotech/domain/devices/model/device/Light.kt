package com.noveogroup.modulotech.domain.devices.model.device

import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType

data class Light(
    override val id: String,
    override val name: String,
    val mode: DeviceMode,
    val intensity: Int,
) : Device {
    override val deviceType: DeviceType = DeviceType.Light
}