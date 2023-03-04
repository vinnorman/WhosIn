package com.gonativecoders.whosin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.ui.navigation.HomeDestinations
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.gonativecoders.whosin.ui.navigation.OnboardingDestinations
import com.gonativecoders.whosin.ui.util.SnackbarManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarManager: SnackbarManager = SnackbarManager,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    dataStore: DataStoreRepository = get()
) = remember(navController, snackbarHostState, snackbarManager, coroutineScope) {
    AppState(
        navController,
        snackbarHostState,
        snackbarManager,
        coroutineScope,
        dataStore
    )
}

@Stable
class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val snackbarManager: SnackbarManager,
    val coroutineScope: CoroutineScope,
    val dataStore: DataStoreRepository
) {

    val homeDestinations =
        listOf(HomeDestinations.WhosIn, HomeDestinations.Chat, HomeDestinations.Account)
    private val bottomNavRoutes = homeDestinations.map { it.route }

    val isBottomNavigationRoute: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomNavRoutes
    val currentRoute: String? get() = navController.currentDestination?.route

    val toolbarTitle = mutableStateOf("")

    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { message ->
                snackbarHostState.showSnackbar(message)
            }
        }
        Firebase.auth.currentUser
    }

    fun navigateToBottomBarRoute(route: String) {
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


    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

    fun onLoggedIn() {
        coroutineScope.launch {
            if (dataStore.getBoolean("has-completed-onboarding")) {
                clearAndNavigate(MainDestinations.Onboarding.route)
            } else {
                clearAndNavigate(MainDestinations.Home.route)
            }
        }
    }

    fun onLoggedOut() {
        Firebase.auth.signOut()
        clearAndNavigate(MainDestinations.Login.route)
    }

    fun onCreateNewTeam() {
        navigate(OnboardingDestinations.CreateTeam.route)
    }

}