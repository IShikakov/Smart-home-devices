package com.noveogroup.modulotech.ui.devices.details.model

import kotlin.math.roundToInt

sealed class DeviceDetailsPreview {
    abstract val id: String
    abstract val name: String
    abstract val rawValue: Float
    abstract val valueRange: ClosedFloatingPointRange<Float>
    abstract val valueStep: Float

    val value: Float
        get() = (rawValue / valueStep).roundToInt() * valueStep
}
