package com.group8.mobilefinalproject.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.group8.mobilefinalproject.R

/** One tab in the bottom navigation bar. */
data class BottomNavItem(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
)

// Tabs shown in the bottom navigation bar.
// Each team member adds their tab here when their screen is ready
// (My Trips, Explore, Profile).
val bottomNavItems = listOf(
    BottomNavItem(Screen.Home.route, R.string.nav_home, Icons.Filled.Home)
)
