package com.noveogroup.modulotech.ui.devices.list.model

import com.noveogroup.modulotech.domain.devices.model.device.DeviceType

data class DevicesFilter(
    val title: String,
    val type: DeviceType,
    val isSelected: Boolean,
)
