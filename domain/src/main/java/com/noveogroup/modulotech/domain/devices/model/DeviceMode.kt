package com.noveogroup.modulotech.domain.devices.model

enum class DeviceMode {
    ON,
    OFF,
    ;

    fun opposite(): DeviceMode = when (this) {
        ON -> OFF
        OFF -> ON
    }
}