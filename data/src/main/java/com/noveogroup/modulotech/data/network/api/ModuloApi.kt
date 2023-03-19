package com.noveogroup.modulotech.data.network.api

import com.noveogroup.modulotech.data.network.response.ModuloResponse
import retrofit2.http.GET

internal interface ModuloApi {

    @GET("modulotest/data.json")
    suspend fun fetchData(): ModuloResponse

}