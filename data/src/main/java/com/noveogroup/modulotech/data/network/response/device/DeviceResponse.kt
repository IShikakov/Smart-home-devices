package com.noveogroup.modulotech.data.network.response.device

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class DeviceResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "productType") val productType: DeviceResponseType?,
    @Json(name = "deviceName") val deviceName: String?,
    @Json(name = "intensity") val intensity: Int?,
    @Json(name = "mode") val mode: DeviceResponseMode?,
    @Json(name = "position") val position: Int?,
    @Json(name = "temperature") val temperature: Float?,
)
