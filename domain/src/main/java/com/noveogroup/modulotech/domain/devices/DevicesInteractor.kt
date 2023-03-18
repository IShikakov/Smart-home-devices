package com.noveogroup.modulotech.domain.devices

import com.noveogroup.modulotech.domain.devices.model.Device
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class DevicesInteractor {

    private val devices = listOf(
        Device.Light(
            id = 0,
            name = "Lampe",
            isOn = true,
            intensity = 50
        ),
        Device.Heater(
            id = 1,
            name = "Rediateur",
            isOn = true,
            temperature = 23.0f
        ),
        Device.RollerShutter(
            id = 2,
            name = "Volet roulant",
            position = 20
        )
    )

    private val devicesFlow: MutableStateFlow<List<Device>> = MutableStateFlow(devices)

    private val availableFilters: List<DeviceType> = DeviceType.values().toList()

    private val selectedFilters: MutableStateFlow<Set<DeviceType>> =
        MutableStateFlow(availableFilters.toSet())

    fun observeDevices(): Flow<List<Device>> {
        return combine(devicesFlow, selectedFilters) { devices, selectedFilters ->
            devices.filter { device -> selectedFilters.contains(device.deviceType) }
        }
    }

    suspend fun refreshDevices() {
        delay(1000)
        devicesFlow.value = devices
    }

    suspend fun deleteDevice(deviceId: Long) {
        devicesFlow.value = devicesFlow.value.filter { device -> device.id != deviceId }
    }

    fun observeFilters(): Flow<FiltersState> {
        return selectedFilters.map { selectedFilters ->
            FiltersState(
                availableFilters = availableFilters,
                selectedFilters = selectedFilters
            )
        }
    }

    fun toggleDevicesFilter(deviceType: DeviceType) {
        val currentlySelectedFilters = selectedFilters.value.toMutableSet()
        selectedFilters.value = currentlySelectedFilters.apply {
            if (contains(deviceType)) remove(deviceType) else add(deviceType)
        }
    }
}