package com.noveogroup.modulotech.ui.devices.details.common

import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.ui.devices.details.model.DeviceDetailsPreview
import kotlin.math.min

object DeviceDetailsMapper {

    private const val MIN_INTENSITY = 0f
    private const val MAX_INTENSITY = 100f
    private const val INTENSITY_STEP = 1f

    private const val MIN_TEMPERATURE = 7f
    private const val MAX_TEMPERATURE = 28f
    private const val TEMPERATURE_STEP = 0.5f

    private const val MIN_ROLLER_POSITION = 0f
    private const val MAX_ROLLER_POSITION = 100f
    private const val ROLLER_POSITION_STEP = 1f

    fun mapToPreview(device: Device): DeviceDetailsPreview = when (device) {
        is Device.Light -> device.preview
        is Device.Heater -> device.preview
        is Device.RollerShutter -> device.preview
    }

    fun mapToDevice(preview: DeviceDetailsPreview): Device = when (preview) {
        is DeviceDetailsPreview.Light -> preview.device
        is DeviceDetailsPreview.Heater -> preview.device
        is DeviceDetailsPreview.RollerShutter -> preview.device
    }

    private val Device.Light.preview: DeviceDetailsPreview.Light
        get() = DeviceDetailsPreview.Light(
            id = id,
            name = name,
            rawValue = min(intensity.toFloat(), MAX_INTENSITY),
            valueRange = MIN_INTENSITY..MAX_INTENSITY,
            valueStep = INTENSITY_STEP,
            mode = mode,
        )

    private val Device.Heater.preview: DeviceDetailsPreview.Heater
        get() = DeviceDetailsPreview.Heater(
            id = id,
            name = name,
            rawValue = min(temperature, MAX_TEMPERATURE),
            valueRange = MIN_TEMPERATURE..MAX_TEMPERATURE,
            valueStep = TEMPERATURE_STEP,
            mode = mode,
        )

    private val Device.RollerShutter.preview: DeviceDetailsPreview.RollerShutter
        get() = DeviceDetailsPreview.RollerShutter(
            id = id,
            name = name,
            rawValue = min(position.toFloat(), MAX_ROLLER_POSITION),
            valueRange = MIN_ROLLER_POSITION..MAX_ROLLER_POSITION,
            valueStep = ROLLER_POSITION_STEP,
        )

    private val DeviceDetailsPreview.Light.device: Device.Light
        get() = Device.Light(
            id = id,
            name = name,
            intensity = value.toInt(),
            mode = mode,
        )

    private val DeviceDetailsPreview.Heater.device: Device.Heater
        get() = Device.Heater(
            id = id,
            name = name,
            temperature = value,
            mode = mode,
        )

    private val DeviceDetailsPreview.RollerShutter.device: Device.RollerShutter
        get() = Device.RollerShutter(
            id = id,
            name = name,
            position = value.toInt(),
        )
}
