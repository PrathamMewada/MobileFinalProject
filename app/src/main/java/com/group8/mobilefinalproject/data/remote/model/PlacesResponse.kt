package com.group8.mobilefinalproject.data.remote.model

import com.google.gson.annotations.SerializedName

data class PlacesResponse(
    val xid: String?,
    val name: String?,
    val rate: Int? = null,
    val osm: String? = null,
    val wikidata: String? = null,
    val kinds: String? = null,
    val dist: Double? = null,
    val point: PlacePoint? = null
)

data class PlacePoint(
    val lon: Double?,
    val lat: Double?
)

data class PlaceDetailResponse(
    val xid: String?,
    val name: String?,
    val rate: Int? = null,
    val osm: String? = null,
    val wikidata: String? = null,
    val kinds: String? = null,
    val sources: PlaceSources? = null,
    val otm: String? = null,
    val wikipedia: String? = null,
    val image: String? = null,
    val preview: PlacePreview? = null,
    val address: PlaceAddress? = null,
    val point: PlacePoint? = null,
    @SerializedName("wikipedia_extracts")
    val wikipediaExtracts: WikipediaExtracts? = null,
    val info: PlaceInfo? = null
)

data class PlaceSources(
    val geometry: String? = null,
    val attributes: List<String>? = null
)

data class PlacePreview(
    val source: String? = null,
    val height: Int? = null,
    val width: Int? = null
)

data class PlaceAddress(
    val city: String? = null,
    val road: String? = null,
    val house: String? = null,
    val postcode: String? = null,
    val country: String? = null,
    @SerializedName("country_code") val countryCode: String? = null,
    val state: String? = null
)

data class WikipediaExtracts(
    val title: String? = null,
    val text: String? = null,
    val html: String? = null
)

data class PlaceInfo(
    val descr: String? = null,
    val image: String? = null,
    val imageWidth: Int? = null,
    val imageHeight: Int? = null,
    val source: String? = null
)
