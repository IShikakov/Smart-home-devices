package com.noveogroup.modulotech.data.network.response.device

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
internal enum class DeviceResponseType {
    @Json(name = "Light")
    Light,

    @Json(name = "Heater")
    Heater,

    @Json(name = "RollerShutter")
    RollerShutter,
}
