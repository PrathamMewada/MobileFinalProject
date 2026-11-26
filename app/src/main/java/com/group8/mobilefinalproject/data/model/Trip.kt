package com.group8.mobilefinalproject.data.model

/**
 * A trip that the user is planning or has already taken.
 *
 * - [id] is the SQLite row id. It is 0 for a new trip that has not been saved yet;
 *   TripRepository.insertTrip returns the trip with its new id. The same number is
 *   used in navigation routes (trip_detail/{tripId}) and as the Firestore document id.
 * - [startDate] and [endDate] are UTC midnight in epoch milliseconds. This is the
 *   same format the Material 3 DatePicker returns, so the trip form can save the
 *   picked value directly.
 * - [estimatedCost] is filled in by BudgetCalculator when a plan is generated and
 *   is shown as the "$X of $Y" budget bar on the trip card.
 * - [updatedAt] is used by the Firestore sync to keep the newest copy of a trip.
 */
data class Trip(
    val id: Long = 0,
    val name: String,
    val country: String,
    val city: String,
    val startDate: Long,
    val endDate: Long,
    val budget: Double,
    val estimatedCost: Double = 0.0,
    val travelStyle: TravelStyle = TravelStyle.MID_RANGE,
    val updatedAt: Long = System.currentTimeMillis()
) {

    /** Number of days in the trip, counting both the first and the last day. */
    val dayCount: Int
        get() = ((endDate - startDate) / DAY_IN_MILLIS).toInt() + 1

    /** Share of the budget used by the estimated cost (1.0 means exactly on budget). */
    val budgetUsage: Float
        get() = if (budget > 0) (estimatedCost / budget).toFloat() else 0f

    /** True when the estimated cost is higher than the budget. */
    val isOverBudget: Boolean
        get() = estimatedCost > budget

    /** A trip is in the past once its last day is before [todayUtc]. */
    fun isPast(todayUtc: Long): Boolean = endDate < todayUtc

    companion object {
        const val DAY_IN_MILLIS = 24L * 60 * 60 * 1000
    }
}
