package com.gonativecoders.whosin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.ui.auth.LoginScreen
import com.gonativecoders.whosin.ui.auth.RegisterScreen
import com.gonativecoders.whosin.ui.home.HomeScreen
import com.gonativecoders.whosin.ui.splash.SplashScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberAppState()
            WhosInTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation(appState)
                }
            }
        }

    }

    @Composable
    fun MainNavigation(appState: AppState) {
        NavHost(navController = appState.navController, startDestination = MainDestinations.Splash.route) {
            composable(MainDestinations.Splash.route) {
                SplashScreen(
                    navigate = { route -> appState.navigate(route = route, clear = true) },
                    onLoggedIn = { user -> appState.setLoggedIn(user) }
                )
            }
            composable(MainDestinations.Login.route) {
                LoginScreen(
                    navigate = { route -> appState.navigate(route) },
                    onLoggedIn = { user -> appState.setLoggedIn(user) }
                )
            }
            composable(MainDestinations.Register.route) {
                RegisterScreen(
                    navigate = { route -> appState.navigate(route = route, popUpDestination = route) },
                    onLoggedIn = { user -> appState.setLoggedIn(user) }
                )
            }
            composable(MainDestinations.Home.route) {
                val user = (appState.loginState as? AppState.LoginState.LoggedIn)?.user ?: kotlin.run {
                    return@composable
                }
                HomeScreen(
                    user = user,
                    onLoggedOut = { appState.setLoggedOut() }
                )
            }
        }
    }
}