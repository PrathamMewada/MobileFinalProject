package com.group8.mobilefinalproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class GeoNameResponse(
    val name: String?,
    val country: String?,
    val lat: Double,
    val lon: Double,
    val population: Long? = null,
    val timezone: String? = null,
    @SerializedName("status") val status: String? = null
)
