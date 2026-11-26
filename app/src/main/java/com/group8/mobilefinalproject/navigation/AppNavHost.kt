package com.group8.mobilefinalproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.group8.mobilefinalproject.ui.screens.home.HomeScreen
import com.group8.mobilefinalproject.ui.screens.splash.SplashScreen

/**
 * Navigation graph of the app.
 * Each screen receives navigation actions as lambdas,
 * so the screens do not depend on the NavController directly.
 *
 * Team members add their own screens here with composable(Screen.XXX.route).
 * All routes are defined in Screen.kt.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    // Remove Splash from the back stack so Back does not return to it
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onTripClick = { tripId ->
                    // Navigate only after the Trip Detail screen (#20) is added to this graph,
                    // so the app does not crash before that
                    if (navController.graph.findNode(Screen.TripDetail.route) != null) {
                        navController.navigate(Screen.TripDetail.createRoute(tripId))
                    }
                }
            )
        }
    }
}
