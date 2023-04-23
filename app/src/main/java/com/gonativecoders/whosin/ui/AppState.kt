package com.gonativecoders.whosin.ui

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
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
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    dataStore: DataStoreRepository = get()
) = remember(navController, coroutineScope) {
    AppState(
        navController,
        coroutineScope,
        dataStore
    )
}

@Stable
class AppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val dataStore: DataStoreRepository
) {

    var loginState by mutableStateOf<LoginState>(LoginState.LoggedOut)
        private set

    fun setLoggedIn(user: User) {
        coroutineScope.launch {
            loginState = LoginState.LoggedIn(user)
            navigate(route = MainDestinations.Home.route, clear = true)
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