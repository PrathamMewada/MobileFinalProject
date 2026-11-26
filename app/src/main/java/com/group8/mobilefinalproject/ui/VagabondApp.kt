package com.group8.mobilefinalproject.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.group8.mobilefinalproject.R
import com.group8.mobilefinalproject.navigation.AppNavHost
import com.group8.mobilefinalproject.navigation.Screen
import com.group8.mobilefinalproject.navigation.bottomNavItems
import com.group8.mobilefinalproject.ui.components.BottomNavBar

/**
 * Root composable of the app.
 * Shows the bottom navigation bar on the tab screens
 * and the "New Trip" FAB on Home and My Trips.
 */
@Composable
fun VagabondApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = bottomNavItems.any { it.route == currentRoute }
    val showFab = currentRoute == Screen.Home.route || currentRoute == Screen.MyTrips.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController, currentRoute = currentRoute)
            }
        },
        floatingActionButton = {
            if (showFab) {
                ExtendedFloatingActionButton(
                    onClick = {
                        // TODO (#16): navigate to Screen.AddEditTrip.createRoute()
                        // after the Add/Edit Trip screen is added to AppNavHost
                    },
                    icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                    text = { Text(stringResource(R.string.fab_new_trip)) },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
