package com.group8.mobilefinalproject.data.repository

import com.group8.mobilefinalproject.data.remote.ApiResult
import com.group8.mobilefinalproject.data.remote.OpenTripMapApiService
import com.group8.mobilefinalproject.data.remote.model.GeoNameResponse
import com.group8.mobilefinalproject.data.remote.model.PlaceDetailResponse
import com.group8.mobilefinalproject.data.remote.model.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Repository is the single place where the app communicates with OpenTripMap.
 * It converts network exceptions into ApiResult.Error so the ViewModel/UI
 * can handle failures without crashing.
 */
class PlacesRepository(
    private val api: OpenTripMapApiService,
    private val apiKey: String
) {
    suspend fun getCityCoordinates(cityName: String): ApiResult<GeoNameResponse> =
        safeApiCall {
            require(cityName.isNotBlank()) { "Please enter a city name." }
            api.getCityCoordinates(cityName.trim(), apiKey)
        }

    suspend fun getNearbyPlaces(
        latitude: Double,
        longitude: Double,
        radiusMeters: Int = 5000,
        limit: Int = 20
    ): ApiResult<List<PlacesResponse>> = safeApiCall {
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90." }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180." }
        require(radiusMeters > 0) { "Radius must be greater than zero." }
        api.getNearbyPlaces(
            radiusMeters = radiusMeters,
            longitude = longitude,
            latitude = latitude,
            limit = limit,
            apiKey = apiKey
        )
    }

    suspend fun getPlaceDetails(xid: String): ApiResult<PlaceDetailResponse> =
        safeApiCall {
            require(xid.isNotBlank()) { "Place ID (xid) is missing." }
            api.getPlaceDetails(xid.trim(), apiKey)
        }

    private suspend fun <T> safeApiCall(
        request: suspend () -> T
    ): ApiResult<T> = withContext(Dispatchers.IO) {
        try {
            if (apiKey.isBlank()) {
                return@withContext ApiResult.Error(
                    "OpenTripMap API key is missing. Add OPENTRIPMAP_API_KEY to local.properties."
                )
            }
            ApiResult.Success(request())
        } catch (exception: IOException) {
            ApiResult.Error(
                "Network connection failed. Check your internet connection and try again.",
                exception
            )
        } catch (exception: HttpException) {
            val message = when (exception.code()) {
                401, 403 -> "The API key is invalid or does not have permission."
                404 -> "The requested city or place could not be found."
                429 -> "Too many requests. Please wait and try again."
                in 500..599 -> "OpenTripMap is temporarily unavailable. Try again later."
                else -> "Server error (${exception.code()}). Please try again."
            }
            ApiResult.Error(message, exception)
        } catch (exception: IllegalArgumentException) {
            ApiResult.Error(exception.message ?: "Please check the entered information.", exception)
        } catch (exception: Exception) {
            ApiResult.Error(
                exception.localizedMessage ?: "Something went wrong. Please try again.",
                exception
            )
        }
    }
}
