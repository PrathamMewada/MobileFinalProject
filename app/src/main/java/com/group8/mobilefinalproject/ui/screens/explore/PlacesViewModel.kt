package com.group8.mobilefinalproject.ui.screens.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.group8.mobilefinalproject.data.remote.ApiResult
import com.group8.mobilefinalproject.data.remote.model.GeoNameResponse
import com.group8.mobilefinalproject.data.remote.model.PlaceDetailResponse
import com.group8.mobilefinalproject.data.remote.model.PlacesResponse
import com.group8.mobilefinalproject.data.repository.PlacesRepository
import com.group8.mobilefinalproject.data.repository.PlacesRepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Example ViewModel showing how to expose Loading / Success / Error to Compose.
 * Other team members can collect these StateFlows in the Explore screen.
 */
class PlacesViewModel(
    private val repository: PlacesRepository = PlacesRepositoryProvider.repository
) : ViewModel() {

    private val _cityResult =
        MutableStateFlow<ApiResult<GeoNameResponse>>(ApiResult.Loading)
    val cityResult: StateFlow<ApiResult<GeoNameResponse>> = _cityResult.asStateFlow()

    private val _nearbyPlacesResult =
        MutableStateFlow<ApiResult<List<PlacesResponse>>>(ApiResult.Loading)
    val nearbyPlacesResult: StateFlow<ApiResult<List<PlacesResponse>>> =
        _nearbyPlacesResult.asStateFlow()

    private val _placeDetailResult =
        MutableStateFlow<ApiResult<PlaceDetailResponse>>(ApiResult.Loading)
    val placeDetailResult: StateFlow<ApiResult<PlaceDetailResponse>> =
        _placeDetailResult.asStateFlow()

    fun searchCity(cityName: String) {
        viewModelScope.launch {
            _cityResult.value = ApiResult.Loading
            _cityResult.value = repository.getCityCoordinates(cityName)
        }
    }

    fun loadNearbyPlaces(
        latitude: Double,
        longitude: Double,
        radiusMeters: Int = 5000
    ) {
        viewModelScope.launch {
            _nearbyPlacesResult.value = ApiResult.Loading
            _nearbyPlacesResult.value = repository.getNearbyPlaces(
                latitude = latitude,
                longitude = longitude,
                radiusMeters = radiusMeters
            )
        }
    }

    fun loadPlaceDetails(xid: String) {
        viewModelScope.launch {
            _placeDetailResult.value = ApiResult.Loading
            _placeDetailResult.value = repository.getPlaceDetails(xid)
        }
    }
}
