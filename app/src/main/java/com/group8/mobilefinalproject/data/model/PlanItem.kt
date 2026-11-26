package com.group8.mobilefinalproject.data.model

/**
 * One place in a trip's day-by-day itinerary.
 *
 * - [tripId] links the item to its [Trip]. Deleting the trip deletes its plan items.
 * - [xid] is the OpenTripMap place id, used to load the Place Detail screen.
 * - [dayNumber] starts at 1 and [orderInDay] starts at 1 inside each day.
 *   Both are set by ItineraryPlanner.
 */
data class PlanItem(
    val id: Long = 0,
    val tripId: Long,
    val xid: String,
    val placeName: String,
    val category: String,
    val dayNumber: Int,
    val orderInDay: Int
)
