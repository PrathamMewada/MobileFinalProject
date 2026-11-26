package com.group8.mobilefinalproject.ui.screens.home

import androidx.lifecycle.ViewModel
import com.group8.mobilefinalproject.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

/**
 * ViewModel for the Home screen.
 * Holds the screen state in a StateFlow so the UI updates automatically.
 */
class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState(greetingRes = getGreeting(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)))
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        _uiState.update { state ->
            state.copy(
                // TODO (M3): load the next trip from TripRepository (#2) instead of sample data
                upcomingTrip = UpcomingTrip(
                    id = 1L,
                    name = "Ottawa Winter Trip",
                    location = "Ottawa, ON",
                    dateRange = "Dec 20 – 27, 2026",
                    daysUntil = 37,
                    budgetSpent = 1850,
                    budgetTotal = 2500
                ),
                // Fixed list of popular destinations
                popularCities = listOf(
                    PopularCity("Toronto", "Ontario"),
                    PopularCity("Ottawa", "Ontario"),
                    PopularCity("Kingston", "Ontario"),
                    PopularCity("Niagara Falls", "Ontario")
                ),
                suggestedPlaces = listOf(
                    SuggestedPlace("CN Tower", "Toronto", 3),
                    SuggestedPlace("Niagara Falls", "Niagara", 3),
                    SuggestedPlace("Parliament Hill", "Ottawa", 3)
                )
            )
        }
    }

    /** Called when the user types in the search bar. */
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    /** Picks the greeting text based on the hour of the day (0–23). */
    private fun getGreeting(hour: Int): Int = when (hour) {
        in 5..11 -> R.string.home_greeting_morning
        in 12..17 -> R.string.home_greeting_afternoon
        else -> R.string.home_greeting_evening
    }
}
