package com.noveogroup.modulotech.ui.devices.list.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.noveogroup.modulotech.R
import com.noveogroup.modulotech.domain.devices.model.Device
import com.noveogroup.modulotech.domain.devices.model.DeviceType
import com.noveogroup.modulotech.domain.devices.model.FiltersState
import com.noveogroup.modulotech.ui.common.ResourcesManager
import com.noveogroup.modulotech.ui.devices.list.model.DevicePreview
import com.noveogroup.modulotech.ui.devices.list.model.DevicesFilter

class DevicesListMapper(private val resourcesManager: ResourcesManager) {

    fun mapDevice(devices: List<Device>): List<DevicePreview> {
        return devices.map { device ->
            with(device) {
                DevicePreview(
                    id = id,
                    name = name,
                    icon = icon,
                    stateDescription = stateDescription
                )
            }
        }
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
            is Device.Light -> "${convertMode(isOn)}, $intensity"
            is Device.Heater -> "${convertMode(isOn)}, ${this.temperature} $CELSIUS_METRIC"
            is Device.RollerShutter -> "$position"
        }

    fun mapFilter(filtersState: FiltersState): List<DevicesFilter> {
        return filtersState.availableFilters.map { type ->
            DevicesFilter(
                title = resourcesManager.resolveString(type.title),
                type = type,
                isSelected = filtersState.selectedFilters.contains(type)
            )
        }
    }

    @get:StringRes
    private val DeviceType.title: Int
        get() = when (this) {
            DeviceType.LIGHT -> R.string.lights_filter
            DeviceType.HEATER -> R.string.heaters_fiter
            DeviceType.ROLLER_SHUTTER -> R.string.roller_shutters_filter
        }

    companion object {
        private const val CELSIUS_METRIC = "°C"
        private fun convertMode(isOn: Boolean): String = if (isOn) "ON" else "OFF"
    }
}