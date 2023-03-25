package com.noveogroup.modulotech.ui.devices.details.common

import com.noveogroup.modulotech.domain.devices.model.device.Device
import com.noveogroup.modulotech.domain.devices.model.device.Heater
import com.noveogroup.modulotech.domain.devices.model.device.Light
import com.noveogroup.modulotech.domain.devices.model.device.RollerShutter
import com.noveogroup.modulotech.ui.devices.details.model.DeviceDetailsPreview
import com.noveogroup.modulotech.ui.devices.details.model.HeaterDetailsPreview
import com.noveogroup.modulotech.ui.devices.details.model.LightDetailsPreview
import com.noveogroup.modulotech.ui.devices.details.model.RollerShutterDetailsPreview
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
        is Light -> device.preview
        is Heater -> device.preview
        is RollerShutter -> device.preview
    }

    fun mapToDevice(preview: DeviceDetailsPreview): Device = when (preview) {
        is LightDetailsPreview -> preview.device
        is HeaterDetailsPreview -> preview.device
        is RollerShutterDetailsPreview -> preview.device
    }

    private val Light.preview: LightDetailsPreview
        get() = LightDetailsPreview(
            id = id,
            name = name,
            rawValue = min(intensity.toFloat(), MAX_INTENSITY),
            valueRange = MIN_INTENSITY..MAX_INTENSITY,
            valueStep = INTENSITY_STEP,
            mode = mode
        )

    private val Heater.preview: HeaterDetailsPreview
        get() = HeaterDetailsPreview(
            id = id,
            name = name,
            rawValue = min(temperature, MAX_TEMPERATURE),
            valueRange = MIN_TEMPERATURE..MAX_TEMPERATURE,
            valueStep = TEMPERATURE_STEP,
            mode = mode
        )

    private val RollerShutter.preview: RollerShutterDetailsPreview
        get() = RollerShutterDetailsPreview(
            id = id,
            name = name,
            rawValue = min(position.toFloat(), MAX_ROLLER_POSITION),
            valueRange = MIN_ROLLER_POSITION..MAX_ROLLER_POSITION,
            valueStep = ROLLER_POSITION_STEP,
        )

    private val LightDetailsPreview.device: Light
        get() = Light(
            id = id,
            name = name,
            intensity = value.toInt(),
            mode = mode
        )

    private val HeaterDetailsPreview.device: Heater
        get() = Heater(
            id = id,
            name = name,
            temperature = value,
            mode = mode
        )

    private val RollerShutterDetailsPreview.device: RollerShutter
        get() = RollerShutter(
            id = id,
            name = name,
            position = value.toInt()
        )
}