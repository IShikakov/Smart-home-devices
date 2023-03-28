package com.noveogroup.modulotech.domain.devices.model

enum class DeviceMode {
    On,
    Off,
    ;

    fun opposite(): DeviceMode = when (this) {
        On -> Off
        Off -> On
    }
}
