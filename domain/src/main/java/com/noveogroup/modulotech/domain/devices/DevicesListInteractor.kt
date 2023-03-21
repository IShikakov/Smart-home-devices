package com.noveogroup.modulotech.domain.devices

import com.noveogroup.modulotech.domain.devices.model.Device
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class DevicesListInteractor(
    private val devicesRepository: DevicesRepositoryApi,
) {

    private val availableFilters: List<DeviceType> = DeviceType.values().toList()
    private val selectedFilters: MutableStateFlow<Set<DeviceType>> =
        MutableStateFlow(availableFilters.toSet())

    fun observeDevices(): Flow<List<Device>> = combine(
        devicesRepository.observeDevices(),
        selectedFilters
    ) { devices, selectedFilters ->
        devices.filter { device -> selectedFilters.contains(device.deviceType) }
    }

    suspend fun deleteDevice(deviceId: String) {
        devicesRepository.deleteDevice(deviceId)
    }

    fun observeFilters(): Flow<FiltersState> = selectedFilters
        .map { selectedFilters ->
            FiltersState(
                availableFilters = availableFilters,
                selectedFilters = selectedFilters
            )
        }


    fun toggleDevicesFilter(deviceType: DeviceType) {
        selectedFilters.update { filters ->
            filters.toMutableSet().apply {
                if (contains(deviceType)) remove(deviceType) else add(deviceType)
            }
        }
    }
}