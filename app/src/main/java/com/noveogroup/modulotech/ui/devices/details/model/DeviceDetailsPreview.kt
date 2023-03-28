package com.noveogroup.modulotech.ui.devices.details.model

import com.noveogroup.modulotech.domain.devices.model.device.DeviceMode
import kotlin.math.roundToInt

sealed class DeviceDetailsPreview {
    abstract val id: String
    abstract val name: String
    abstract val rawValue: Float
    abstract val valueRange: ClosedFloatingPointRange<Float>
    abstract val valueStep: Float

    val value: Float
        get() = (rawValue / valueStep).roundToInt() * valueStep

    data class Heater(
        override val id: String,
        override val name: String,
        override val rawValue: Float,
        override val valueRange: ClosedFloatingPointRange<Float>,
        override val valueStep: Float,
        val mode: DeviceMode,
    ) : DeviceDetailsPreview()

    data class Light(
        override val id: String,
        override val name: String,
        override val rawValue: Float,
        override val valueRange: ClosedFloatingPointRange<Float>,
        override val valueStep: Float,
        val mode: DeviceMode,
    ) : DeviceDetailsPreview()

    data class RollerShutter(
        override val id: String,
        override val name: String,
        override val rawValue: Float,
        override val valueRange: ClosedFloatingPointRange<Float>,
        override val valueStep: Float,
    ) : DeviceDetailsPreview()
}
