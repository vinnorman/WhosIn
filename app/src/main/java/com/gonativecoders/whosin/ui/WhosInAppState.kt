package com.gonativecoders.whosin.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.ui.navigation.Screen


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) { AppState(navController) }

@Stable
class AppState(val navController: NavHostController) {

    val isBottomNavigationRoute: Boolean
        @Composable get() = when (navController.currentBackStackEntryAsState().value?.destination?.route) {
            Screen.Login.route -> false
            else -> true
        }

}