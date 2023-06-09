package com.noveogroup.modulotech.ui.devices.list.model

import androidx.annotation.DrawableRes

data class DevicePreview(
    val id: String,
    val name: String,
    @DrawableRes val icon: Int,
    val stateDescription: String,
)
