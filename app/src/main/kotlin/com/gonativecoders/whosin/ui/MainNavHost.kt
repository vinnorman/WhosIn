package com.gonativecoders.whosin.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.ui.auth.CreateAccountScreen
import com.gonativecoders.whosin.ui.auth.LoginScreen
import com.gonativecoders.whosin.ui.home.HomeScreen
import com.gonativecoders.whosin.ui.home.createteam.CreateTeamScreen
import com.gonativecoders.whosin.ui.home.jointeam.JoinTeamScreen
import com.gonativecoders.whosin.ui.onboarding.profilesetup.ProfileSetupScreen
import com.gonativecoders.whosin.ui.onboarding.welcome.WelcomeScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

sealed class MainDestinations(val route: String) {
    object Splash : MainDestinations("splash")
    object Login : MainDestinations("login")
    object CreateAccount : MainDestinations("create_account")
    object Home : MainDestinations("home")
    object ProfileSetup : MainDestinations("profile_setup")
    object Welcome : MainDestinations("welcome")
    object CreateTeam : MainDestinations("create_team")
    object JoinTeam : MainDestinations("join_team")
}

@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    onLoggedIn: (User) -> Unit,
    onUserUpdated: (User) -> Unit,
    onLoggedOut: () -> Unit,
    onUserLeftTeam: () -> Unit,
    uiState: MainViewModel.UiState,
) {
    NavHost(navController = navController, startDestination = MainDestinations.Splash.route) {
        composable(MainDestinations.Splash.route) {

        }
        composable(MainDestinations.Login.route) {
            LoginScreen(
                navigateToRegisterScreen = { navController.navigate(MainDestinations.CreateAccount) },
                onLoggedIn = onLoggedIn
            )
        }
        composable(MainDestinations.CreateAccount.route) {
            CreateAccountScreen(
                navigateToLoginScreen = {
                    navController.navigate(
                        destination = MainDestinations.Login,
                        popUpDestination = MainDestinations.Login
                    )
                },
                onAccountCreated = onLoggedIn
            )
        }
        composable(MainDestinations.ProfileSetup.route) {
            val user = (uiState as? MainViewModel.UiState.LoggedIn)?.user ?: kotlin.run {
                onLoggedOut()
                return@composable
            }
            ProfileSetupScreen(
                viewModel = getViewModel(parameters = { parametersOf(user) }),
                onProfileSetupComplete = onUserUpdated
            )
        }
        composable(MainDestinations.Welcome.route) {
            WelcomeScreen(
                navigateToCreateTeamScreen = {
                    navController.navigate(
                        destination = MainDestinations.CreateTeam,
                        clear = true
                    )
                },
                navigateToJoinTeamScreen = {
                    navController.navigate(
                        destination = MainDestinations.JoinTeam,
                        clear = true
                    )
                }
            )
        }
        composable(route = MainDestinations.CreateTeam.route) {
            CreateTeamScreen(
                onUserUpdated = onUserUpdated,
                onCreateTeamSuccess = {  },
                onBackArrowPressed = { navController.popBackStack() }
            )
        }
        composable(route = MainDestinations.JoinTeam.route) {
            JoinTeamScreen(
                onUserUpdated = onUserUpdated,
                onBackArrowPressed = { navController.popBackStack() }
            )
        }
        composable(MainDestinations.Home.route) {
            HomeScreen(
                onLoggedOut = onLoggedOut,
                onUserUpdated = onLoggedIn,
                onUserLeftTeam = onUserLeftTeam
            )
        }
    }



}

fun NavHostController.navigate(destination: MainDestinations, clear: Boolean = false, popUpDestination: MainDestinations? = null) {
    navigate(destination.route) {
        launchSingleTop = true
        if (clear) popUpTo(0) { inclusive = true }
        else if (popUpDestination != null) popUpTo(popUpDestination.route) { inclusive = true }
    }
}