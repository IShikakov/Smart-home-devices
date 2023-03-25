package com.noveogroup.modulotech.domain.devices.model.device

import com.noveogroup.modulotech.domain.devices.model.DeviceType

sealed interface Device {
    val id: String
    val name: String
    val deviceType: DeviceType
}
