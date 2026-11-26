package com.group8.mobilefinalproject.ui.screens.home

import androidx.annotation.StringRes

/** Everything the Home screen needs to display. */
data class HomeUiState(
    @StringRes val greetingRes: Int,
    val searchQuery: String = "",
    val upcomingTrip: UpcomingTrip? = null,
    val popularCities: List<PopularCity> = emptyList(),
    val suggestedPlaces: List<SuggestedPlace> = emptyList()
)

/** The next trip shown on the Home card. */
data class UpcomingTrip(
    val id: Long,
    val name: String,
    val location: String,
    val dateRange: String,
    val daysUntil: Int,
    val budgetSpent: Int,
    val budgetTotal: Int
) {
    /** Share of the budget already used (0.0 = nothing, 1.0 = all of it). */
    val budgetProgress: Float
        get() = if (budgetTotal > 0) budgetSpent.toFloat() / budgetTotal else 0f

    val budgetStatus: BudgetStatus
        get() = BudgetStatus.from(budgetProgress)
}

/** Budget state used to pick the color of the budget bar. */
enum class BudgetStatus {
    UNDER,   // less than 90% used
    WARNING, // 90% to 100% used
    OVER;    // more than the budget

    companion object {
        private const val WARNING_LIMIT = 0.9f

        fun from(progress: Float): BudgetStatus = when {
            progress > 1f -> OVER
            progress >= WARNING_LIMIT -> WARNING
            else -> UNDER
        }
    }
}

data class PopularCity(
    val name: String,
    val province: String
)

data class SuggestedPlace(
    val name: String,
    val city: String,
    val rating: Int // OpenTripMap rating from 1 to 3
)
