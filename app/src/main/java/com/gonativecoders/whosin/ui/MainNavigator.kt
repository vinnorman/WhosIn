package com.gonativecoders.whosin.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.auth.CreateAccountScreen
import com.gonativecoders.whosin.ui.auth.LoginScreen
import com.gonativecoders.whosin.ui.home.HomeScreen
import com.gonativecoders.whosin.ui.home.onboarding.profilesetup.ProfileSetupScreen
import com.gonativecoders.whosin.ui.splash.SplashScreen

sealed class MainDestinations(val route: String) {
    object Splash : MainDestinations("splash")
    object Login : MainDestinations("login")
    object Register : MainDestinations("register")
    object Home : MainDestinations("home")
    object Onboarding : MainDestinations("onboarding")
}

@Composable
fun MainNavigator(
    navController: NavHostController = rememberNavController(),
    onLoggedIn: (User) -> Unit,
    onAccountCreated: (User) -> Unit,
    onLoggedOut: () -> Unit,
    onOnboarding: (User) -> Unit,
    uiState: MainViewModel.UiState,
    onUserUpdated: (User) -> Unit
) {
    NavHost(navController = navController, startDestination = MainDestinations.Splash.route) {
        composable(MainDestinations.Splash.route) {
            SplashScreen(
                onLoggedOut = onLoggedOut,
                onLoggedIn = onLoggedIn,
                onOnboarding = onOnboarding
            )
        }
        composable(MainDestinations.Login.route) {
            LoginScreen(
                navigateToRegisterScreen = { navController.navigate(MainDestinations.Register) },
                onLoggedIn = onLoggedIn
            )
        }
        composable(MainDestinations.Onboarding.route) {
            val user = (uiState as? MainViewModel.UiState.Onboarding)?.user ?: kotlin.run {
                onLoggedOut()
                return@composable
            }
            ProfileSetupScreen(
                onProfileSetupComplete = { onLoggedIn(user) }
            )
        }
        composable(MainDestinations.Register.route) {
            CreateAccountScreen(
                navigateToLoginScreen = {
                    navController.navigate(
                        destination = MainDestinations.Login,
                        popUpDestination = MainDestinations.Login
                    )
                },
                onAccountCreated = onAccountCreated
            )
        }
        composable(MainDestinations.Home.route) {
            val user = (uiState as? MainViewModel.UiState.LoggedIn)?.user ?: kotlin.run {
                onLoggedOut()
                return@composable
            }
            HomeScreen(
                user = user,
                onLoggedOut = onLoggedOut,
                onUserUpdated = onUserUpdated
            )
        }
    }

    when (uiState) {
        is MainViewModel.UiState.LoggedIn -> navController.navigate(destination = MainDestinations.Home, clear = true)
        MainViewModel.UiState.LoggedOut -> navController.navigate(destination = MainDestinations.Login, clear = true)
        MainViewModel.UiState.Splash -> navController.navigate(MainDestinations.Splash)
        is MainViewModel.UiState.Onboarding -> navController.navigate(MainDestinations.Onboarding, clear = true)
    }

}

fun NavHostController.navigate(destination: MainDestinations, clear: Boolean = false, popUpDestination: MainDestinations? = null) {
    navigate(destination.route) {
        launchSingleTop = true
        if (clear) popUpTo(0) { inclusive = true }
        else if (popUpDestination != null) popUpTo(popUpDestination.route) { inclusive = true }
    }
}