package com.gonativecoders.whosin.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    dataStore: DataStoreRepository = get()
) = remember(navController, snackbarHostState, coroutineScope) {
    AppState(
        navController,
        snackbarHostState,
        coroutineScope,
        dataStore
    )
}

@Stable
class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope,
    val dataStore: DataStoreRepository
) {

    val homeDestinations = listOf(HomeDestinations.WhosIn, HomeDestinations.Team, HomeDestinations.Account)

    val isBottomNavigationRoute: Boolean
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in homeDestinations.map { it.route }
    val currentRoute: String? get() = navController.currentDestination?.route

    var loginState by mutableStateOf<LoginState>(LoginState.LoggedOut)
        private set

    fun setLoggedIn(user: User) {
        coroutineScope.launch {
            loginState = LoginState.LoggedIn(user)
            val route = if (user.team == null) MainDestinations.Onboarding.route else MainDestinations.Home.route
            navigate(route = route, clear = true)
        }
    }

    fun setLoggedOut() {
        Firebase.auth.signOut()
        loginState = LoginState.LoggedOut
        navigate(route = MainDestinations.Login.route, clear = true)
    }

    fun navigate(route: String, clear: Boolean = false, popUpDestination: String? = null) {
        navController.navigate(route) {
            launchSingleTop = true
            if (clear) popUpTo(0) { inclusive = true }
            else if (popUpDestination != null) popUpTo(popUpDestination) { inclusive = true }
        }
    }

    fun onUserUpdated(user: User) {
        (loginState as? LoginState.LoggedIn)?.let {
            it.user = user
        }
    }

    sealed class LoginState {

        data class LoggedIn(var user: User) : LoginState()

        object LoggedOut : LoginState()

    }

}