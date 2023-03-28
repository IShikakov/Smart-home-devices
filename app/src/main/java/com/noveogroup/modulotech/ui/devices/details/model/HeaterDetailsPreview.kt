package com.noveogroup.modulotech.ui.devices.details.model

import com.noveogroup.modulotech.domain.devices.model.DeviceMode

data class HeaterDetailsPreview(
    override val id: String,
    override val name: String,
    override val rawValue: Float,
    override val valueRange: ClosedFloatingPointRange<Float>,
    override val valueStep: Float,
    val mode: DeviceMode,
) : DeviceDetailsPreview()
