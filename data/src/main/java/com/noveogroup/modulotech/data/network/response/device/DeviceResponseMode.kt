package com.noveogroup.modulotech.data.network.response.device

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
internal enum class DeviceResponseMode {
    @Json(name = "ON")
    On,

    @Json(name = "OFF")
    Off,
}
