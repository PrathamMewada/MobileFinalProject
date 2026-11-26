package com.group8.mobilefinalproject.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.group8.mobilefinalproject.navigation.Screen
import com.group8.mobilefinalproject.navigation.bottomNavItems

/** Bottom navigation bar with the main tabs. */
@Composable
fun BottomNavBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            val label = stringResource(item.labelRes)
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Keep only one copy of each tab in the back stack
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = label) },
                label = { Text(label) }
            )
        }
    }
}
