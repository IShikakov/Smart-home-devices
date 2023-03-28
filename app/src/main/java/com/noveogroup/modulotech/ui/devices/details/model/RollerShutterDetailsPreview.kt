package com.noveogroup.modulotech.ui.devices.details.model

data class RollerShutterDetailsPreview(
    override val id: String,
    override val name: String,
    override val rawValue: Float,
    override val valueRange: ClosedFloatingPointRange<Float>,
    override val valueStep: Float,
) : DeviceDetailsPreview()
