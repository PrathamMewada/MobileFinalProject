package com.group8.mobilefinalproject.navigation

/**
 * All navigation routes in the app.
 * Screens that need data (e.g., a trip id) build their route with createRoute().
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    // Bottom navigation tabs
    data object Home : Screen("home")
    data object MyTrips : Screen("my_trips")
    data object Explore : Screen("explore")
    data object Profile : Screen("profile")

    // Stack screens
    data object TripDetail : Screen("trip_detail/{tripId}") {
        fun createRoute(tripId: Long) = "trip_detail/$tripId"
    }

    // tripId is optional: no id = add a new trip, with id = edit an existing trip
    data object AddEditTrip : Screen("add_edit_trip?tripId={tripId}") {
        fun createRoute(tripId: Long? = null) =
            if (tripId == null) "add_edit_trip" else "add_edit_trip?tripId=$tripId"
    }

    data object PickPlaces : Screen("pick_places/{tripId}") {
        fun createRoute(tripId: Long) = "pick_places/$tripId"
    }

    // xid is the place id from the OpenTripMap API
    data object PlaceDetail : Screen("place_detail/{xid}") {
        fun createRoute(xid: String) = "place_detail/$xid"
    }
}

// Names of the route arguments
const val ARG_TRIP_ID = "tripId"
const val ARG_PLACE_XID = "xid"
const val NO_TRIP_ID = -1L
