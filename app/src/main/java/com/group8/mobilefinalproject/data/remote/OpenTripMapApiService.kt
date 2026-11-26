package com.group8.mobilefinalproject.data.remote

import com.group8.mobilefinalproject.data.remote.model.GeoNameResponse
import com.group8.mobilefinalproject.data.remote.model.PlaceDetailResponse
import com.group8.mobilefinalproject.data.remote.model.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * OpenTripMap API endpoints used by Vagabond.
 * Base URL: https://api.opentripmap.com/
 */
interface OpenTripMapApiService {

    // GET /0.1/en/places/geoname?name=Toronto&apikey=...
    @GET("0.1/en/places/geoname")
    suspend fun getCityCoordinates(
        @Query("name") cityName: String,
        @Query("apikey") apiKey: String
    ): GeoNameResponse

    // GET /0.1/en/places/radius?radius=...&lon=...&lat=...&format=json&limit=...&apikey=...
    @GET("0.1/en/places/radius")
    suspend fun getNearbyPlaces(
        @Query("radius") radiusMeters: Int,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("apikey") apiKey: String
    ): List<PlacesResponse>

    // GET /0.1/en/places/xid/{xid}?apikey=...
    @GET("0.1/en/places/xid/{xid}")
    suspend fun getPlaceDetails(
        @Path("xid") xid: String,
        @Query("apikey") apiKey: String
    ): PlaceDetailResponse
}
