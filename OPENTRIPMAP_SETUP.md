# OpenTripMap API integration (M2)

## 1. Add your API key
1. Create an API key from OpenTripMap.
2. In the Android Studio project root (same folder as `settings.gradle.kts`), create/open `local.properties`.
3. Add this line (replace the placeholder):
   `OPENTRIPMAP_API_KEY=YOUR_REAL_API_KEY`
4. Do not commit `local.properties`. It is already ignored by the project's `.gitignore`.
5. Sync Gradle.

The app module reads the key from `local.properties` and exposes it as
`BuildConfig.OPENTRIPMAP_API_KEY`. Do not paste the real key into Kotlin source code.

## 2. Files added
- `data/remote/OpenTripMapApiService.kt`: the three REST endpoints.
- `data/remote/RetrofitClient.kt`: Retrofit setup.
- `data/remote/model/GeoNameResponse.kt`: JSON response data classes.
- `data/remote/ApiResult.kt`: Loading, Success and Error states.
- `data/repository/PlacesRepository.kt`: calls the API and converts network/HTTP errors into `ApiResult.Error`.
- `data/repository/PlacesRepositoryProvider.kt`: creates the repository using BuildConfig.

## 3. Call examples
```kotlin
val result = PlacesRepositoryProvider.repository.getCityCoordinates("Toronto")
when (result) {
    ApiResult.Loading -> { /* show progress */ }
    is ApiResult.Success -> {
        val latitude = result.data.lat
        val longitude = result.data.lon
    }
    is ApiResult.Error -> { /* show result.message */ }
}
```

```kotlin
val nearby = PlacesRepositoryProvider.repository.getNearbyPlaces(
    latitude = 43.6532,
    longitude = -79.3832,
    radiusMeters = 5000,
    limit = 20
)
```

```kotlin
val detail = PlacesRepositoryProvider.repository.getPlaceDetails("PLACE_XID")
```

These are suspend functions. Call them from `viewModelScope.launch` in a ViewModel,
not directly from a Composable. For a screen state, set Loading before starting
the request, then set the returned Success or Error result after it completes.

## 4. Endpoints
- `GET /0.1/en/places/geoname` — city name to latitude/longitude.
- `GET /0.1/en/places/radius` — nearby attractions using latitude, longitude and radius in metres.
- `GET /0.1/en/places/xid/{xid}` — detailed information for one attraction.
