package com.group8.mobilefinalproject.data.repository

import com.group8.mobilefinalproject.BuildConfig
import com.group8.mobilefinalproject.data.remote.RetrofitClient

object PlacesRepositoryProvider {
    val repository: PlacesRepository by lazy {
        PlacesRepository(
            api = RetrofitClient.api,
            apiKey = BuildConfig.OPENTRIPMAP_API_KEY
        )
    }
}
