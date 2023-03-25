package com.noveogroup.modulotech.domain.devices.model.device

import com.noveogroup.modulotech.domain.devices.model.DeviceType

data class RollerShutter(
    override val id: String,
    override val name: String,
    val position: Int,
) : Device {
    override val deviceType: DeviceType = DeviceType.ROLLER_SHUTTER
}