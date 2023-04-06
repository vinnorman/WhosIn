package com.gonativecoders.whosin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.data.auth.model.User
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

    val homeDestinations = listOf(HomeDestinations.WhosIn, HomeDestinations.Team, HomeDestinations.Account)

    val isBottomNavigationRoute: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in homeDestinations.map { it.route }
    val currentRoute: String? get() = navController.currentDestination?.route

    val loginState = mutableStateOf<LoginState>(LoginState.LoggedOut)
    
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { message ->
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    fun setLoggedIn(user: User) {
        coroutineScope.launch {
            loginState.value = LoginState.LoggedIn(user)
            val route = if (user.team == null) MainDestinations.Onboarding.route else MainDestinations.Home.route + "/${user.id}"
            navigate(route = route, clear = true)
        }
    }

    fun setLoggedOut() {
        Firebase.auth.signOut()
        loginState.value = LoginState.LoggedOut
        navigate(route = MainDestinations.Login.route, clear = true)
    }

    fun navigate(route: String, clear: Boolean = false, popUpDestination: String? = null) {
        navController.navigate(route) {
            launchSingleTop = true
            if (clear) popUpTo(0) { inclusive = true }
            else if (popUpDestination != null) popUpTo(popUpDestination) { inclusive = true }
        }
    }

    fun onCreateNewTeam() {
        navigate(OnboardingDestinations.CreateTeam.route)
    }

    sealed class LoginState {

        data class LoggedIn(val user: User) : LoginState()

        object LoggedOut : LoginState()

    }

}