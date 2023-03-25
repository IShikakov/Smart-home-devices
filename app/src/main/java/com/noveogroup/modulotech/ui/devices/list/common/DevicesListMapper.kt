package com.noveogroup.modulotech.ui.devices.list.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.DeviceMode
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.Heater
import com.noveogroup.modulotech.domain.devices.model.device.Light
import com.noveogroup.modulotech.domain.devices.model.device.RollerShutter
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.devices.list.model.DevicePreview
import com.noveogroup.modulotech.ui.devices.list.model.DevicesFilter

class DevicesListMapper(private val resourcesManager: ResourcesManager) {

    fun mapDevices(devices: List<Device>): List<DevicePreview> = devices.map { device ->
        with(device) {
            DevicePreview(
                id = id,
                name = name,
                icon = icon,
                stateDescription = stateDescription
            )
        }
    }

    fun mapFilters(filtersState: FiltersState): List<DevicesFilter> =
        filtersState.availableFilters.map { type ->
            DevicesFilter(
                title = resourcesManager.resolveString(type.title),
                type = type,
                isSelected = filtersState.selectedFilters.contains(type)
            )
        }

    @get:DrawableRes
    private val Device.icon: Int
        get() = when (this) {
            is Light -> R.drawable.ic_light
            is Heater -> R.drawable.ic_heater
            is RollerShutter -> R.drawable.ic_roller
        }

    private val Device.stateDescription: String
        get() = when (this) {
            is Light -> "${mode.description()}, $intensity"
            is Heater -> "${mode.description()}, ${
                resourcesManager.resolveString(
                    R.string.format_heater_temperature,
                    temperature
                )
            }"
            is RollerShutter -> "$position"
        }

    @get:StringRes
    private val DeviceType.title: Int
        get() = when (this) {
            DeviceType.LIGHT -> R.string.lights_filter
            DeviceType.HEATER -> R.string.heaters_fiter
            DeviceType.ROLLER_SHUTTER -> R.string.roller_shutters_filter
        }

    private fun DeviceMode.description(): String = when (this) {
        DeviceMode.ON -> resourcesManager.resolveString(R.string.device_on)
        DeviceMode.OFF -> resourcesManager.resolveString(R.string.device_off)
    }
}