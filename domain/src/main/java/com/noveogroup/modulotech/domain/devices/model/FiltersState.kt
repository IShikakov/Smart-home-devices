package com.noveogroup.modulotech.domain.devices.model

data class FiltersState(
    val availableFilters: List<DeviceType>,
    val selectedFilters: Set<DeviceType>,
)