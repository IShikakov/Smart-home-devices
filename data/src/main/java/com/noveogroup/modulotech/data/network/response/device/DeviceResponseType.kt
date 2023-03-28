package com.noveogroup.modulotech.data.network.response.device

import com.squareup.moshi.Json

internal enum class DeviceResponseType {
    @Json(name = "Light")
    Light,

    @Json(name = "Heater")
    Heater,

    @Json(name = "RollerShutter")
    RollerShutter,
}
