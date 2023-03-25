package com.noveogroup.modulotech.data.network.response.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class AddressResponse(
    @Json(name = "city") val city: String?,
    @Json(name = "postalCode") val postalCode: String?,
    @Json(name = "street") val street: String?,
    @Json(name = "streetCode") val streetCode: String?,
    @Json(name = "country") val country: String?,
)