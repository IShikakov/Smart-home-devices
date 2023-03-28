package com.noveogroup.modulotech.domain.devices.model.filter

import com.noveogroup.modulotech.domain.devices.model.device.DeviceType

data class FiltersState(
    val availableFilters: List<DeviceType>,
    val selectedFilters: Set<DeviceType>,
)
