package com.noveogroup.modulotech.data.network.response

import com.noveogroup.modulotech.data.network.response.device.DeviceResponse
import com.noveogroup.modulotech.data.network.response.user.UserResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ModuloResponse(
    @Json(name = "devices") val devices: List<DeviceResponse>,
    @Json(name = "user") val user: UserResponse,
)
