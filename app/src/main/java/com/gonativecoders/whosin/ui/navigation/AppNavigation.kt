package com.gonativecoders.whosin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.gonativecoders.whosin.ui.AppState
import com.gonativecoders.whosin.ui.screens.home.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.home.team.TeamScreen
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInViewModel
import com.gonativecoders.whosin.ui.screens.login.LoginScreen
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamScreen
import com.gonativecoders.whosin.ui.screens.onboarding.jointeam.JoinTeamScreen
import com.gonativecoders.whosin.ui.screens.onboarding.welcome.WelcomeScreen
import com.gonativecoders.whosin.ui.screens.register.RegisterScreen
import com.gonativecoders.whosin.ui.screens.splash.SplashScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(
    appState: AppState,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        loginGraph(appState)
        onboardingNavGraph(appState)
        homeNavGraph(appState)
    }
}

private fun NavGraphBuilder.loginGraph(appState: AppState) {
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
}

private fun NavGraphBuilder.onboardingNavGraph(appState: AppState) {
    navigation(route = MainDestinations.Onboarding.route, startDestination = OnboardingDestinations.Welcome.route) {
        composable(OnboardingDestinations.Welcome.route) {
            WelcomeScreen(
                navigate = { route -> appState.navigate(route) }
            )
        }
        composable(OnboardingDestinations.CreateTeam.route) {
            CreateTeamScreen(
                navigate = { route -> appState.navigate(route) }
            )
        }
        composable(OnboardingDestinations.JoinTeam.route) {
            JoinTeamScreen(
                navigate = { route -> appState.navigate(route) }
            )
        }
    }
}

private fun NavGraphBuilder.homeNavGraph(appState: AppState) {
    navigation(
        route = MainDestinations.Home.route + "/{userId}",
        startDestination = HomeDestinations.WhosIn.route,
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        composable(route = HomeDestinations.WhosIn.route) { entry ->
            val userId = appState.getUserId(entry)
            val viewModel: WhosInViewModel = getViewModel(parameters = { parametersOf(userId) })
            WhosInScreen(viewModel)
        }

        composable(HomeDestinations.Team.route) { entry ->
            TeamScreen()
        }
        composable(HomeDestinations.Account.route) { entry ->
            val userId = appState.getUserId(entry)
            AccountScreen(
                onLoggedOut = appState::setLoggedOut,
                onCreateNewTeam = appState::onCreateNewTeam
            )
        }
    }
}

@Composable
private fun AppState.getUserId(entry: NavBackStackEntry): String? {
    return remember(entry) { navController.getBackStackEntry(MainDestinations.Home.route + "/{userId}").arguments?.getString("userId") }
}