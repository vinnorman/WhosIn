package com.gonativecoders.whosin.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.ui.navigation.HomeDestinations
import com.gonativecoders.whosin.ui.navigation.MainDestinations


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) { AppState(navController) }

@Stable
class AppState(val navController: NavHostController) {

    val homeDestinations = listOf(HomeDestinations.WhosIn, HomeDestinations.Me, HomeDestinations.Account)
    private val bottomNavRoutes = homeDestinations.map { it.route }

    val isBottomNavigationRoute: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomNavRoutes
    val currentRoute: String?
        get() = navController.currentDestination?.route

    fun onBottomNavigationSelected(route: String) {
        if (route != currentRoute) {
            navController.navigate(route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                // Avoid multiple copies of the same destination when re-selecting the same item
                launchSingleTop = true
                // Restore state when re-selecting a previously selected item
                restoreState = true
            }
        }
    }

    fun onLoggedIn() {
        navController.navigate(MainDestinations.Home.route) {
            popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                inclusive = true
            }
        }
    }

    fun onLoggedOut() {

    }

}