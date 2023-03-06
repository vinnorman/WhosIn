package com.gonativecoders.whosin.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.gonativecoders.whosin.ui.AppState
import com.gonativecoders.whosin.ui.screens.home.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.home.me.MeScreen
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

fun NavGraphBuilder.navGraph(appState: AppState) {
    composable(MainDestinations.Splash.route) {
        SplashScreen(navigate = appState::clearAndNavigate, onLoggedIn = appState::setLoggedIn)
    }
    composable(MainDestinations.Login.route) {
        LoginScreen(navigate = { route -> appState.navigate(route) }, onLoggedIn = appState::setLoggedIn)
    }
    composable(MainDestinations.Register.route) {
        RegisterScreen(
            onLoggedIn = appState::setLoggedIn,
            navigate = { route -> appState.navigateAndPopUp(route, MainDestinations.Login.route) }
        )
    }
    homeNavGraph(appState)
    onboardingNavGraph(appState)
}

private fun NavGraphBuilder.onboardingNavGraph(appState: AppState) {
    navigation(route = MainDestinations.Onboarding.route, startDestination = OnboardingDestinations.Welcome.route) {
        composable(OnboardingDestinations.Welcome.route) { WelcomeScreen(appState::navigate) }
        composable(OnboardingDestinations.CreateTeam.route) { CreateTeamScreen(appState::navigate) }
        composable(OnboardingDestinations.JoinTeam.route) { JoinTeamScreen(appState::navigate) }
    }
}

private fun NavGraphBuilder.homeNavGraph(appState: AppState) {
    navigation(
        route = MainDestinations.Home.route + "/{userId}",
        startDestination = HomeDestinations.WhosIn.route,
        arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) {
        composable(route = HomeDestinations.WhosIn.route) { entry ->
            val userId = entry.arguments?.getString("userId")
            val viewModel: WhosInViewModel = getViewModel(parameters = { parametersOf(userId) })
            WhosInScreen(viewModel)
        }

        composable(HomeDestinations.Chat.route) { MeScreen() }
        composable(HomeDestinations.Account.route) {
            AccountScreen(
                onLoggedOut = appState::setLoggedOut,
                onCreateNewTeam = appState::onCreateNewTeam
            )
        }
    }
}
