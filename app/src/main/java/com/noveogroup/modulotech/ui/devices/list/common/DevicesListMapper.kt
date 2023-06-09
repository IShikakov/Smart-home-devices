package com.noveogroup.modulotech.ui.devices.list.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.device.DeviceType
import com.noveogroup.modulotech.domain.devices.model.filter.FiltersState
import com.noveogroup.modulotech.ui.common.resources.ResourcesManager
import com.noveogroup.modulotech.ui.devices.list.model.DevicePreview
import com.noveogroup.modulotech.ui.devices.list.model.DevicesFilter

class DevicesListMapper(private val resourcesManager: ResourcesManager) {

    fun mapDevices(devices: List<Device>): List<DevicePreview> = devices.map { device ->
        with(device) {
            DevicePreview(
                id = id,
                name = name,
                icon = icon,
                stateDescription = stateDescription,
            )
        }
    }

    fun mapFilters(filtersState: FiltersState): List<DevicesFilter> =
        filtersState.availableFilters.map { type ->
            DevicesFilter(
                title = resourcesManager.resolveString(type.title),
                type = type,
                isSelected = filtersState.selectedFilters.contains(type),
            )
        }

    @get:DrawableRes
    private val Device.icon: Int
        get() = when (this) {
            is Device.Light -> R.drawable.ic_light
            is Device.Heater -> R.drawable.ic_heater
            is Device.RollerShutter -> R.drawable.ic_roller
        }

    private val Device.stateDescription: String
        get() = when (this) {
            is Device.Light -> "${mode.description()}, $intensity"
            is Device.Heater -> "${mode.description()}, ${
                resourcesManager.resolveString(
                    R.string.Common_temperature_format,
                    temperature,
                )
            }"
            is Device.RollerShutter -> "$position"
        }

    @get:StringRes
    private val DeviceType.title: Int
        get() = when (this) {
            DeviceType.Light -> R.string.Devices_lights_filter
            DeviceType.Heater -> R.string.Devices_heaters_filter
            DeviceType.RollerShutter -> R.string.Devices_roller_shutters_filter
        }

    private fun DeviceMode.description(): String = when (this) {
        DeviceMode.On -> resourcesManager.resolveString(R.string.Devices_device_on)
        DeviceMode.Off -> resourcesManager.resolveString(R.string.Devices_device_off)
    }
}
