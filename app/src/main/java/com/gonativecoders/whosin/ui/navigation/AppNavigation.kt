package com.gonativecoders.whosin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gonativecoders.whosin.ui.AppState
import com.gonativecoders.whosin.ui.screens.home.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.home.team.TeamScreen
import com.gonativecoders.whosin.ui.screens.home.team.TeamViewModel
import com.gonativecoders.whosin.ui.screens.home.teaminfo.TeamInfoScreen
import com.gonativecoders.whosin.ui.screens.home.teaminfo.TeamInfoViewModel
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInViewModel
import com.gonativecoders.whosin.ui.screens.login.LoginScreen
import com.gonativecoders.whosin.ui.screens.login.RegisterScreen
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamScreen
import com.gonativecoders.whosin.ui.screens.onboarding.jointeam.JoinTeamScreen
import com.gonativecoders.whosin.ui.screens.onboarding.welcome.WelcomeScreen
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
                onUserUpdated = { user -> appState.onUserUpdated(user) },
                navigate = { route -> appState.navigate(route) }
            )
        }
        composable(OnboardingDestinations.JoinTeam.route) {
            JoinTeamScreen(
                onUserUpdated = { user -> appState.onUserUpdated(user) },
                navigate = { route -> appState.navigate(route) }
            )
        }
    }
}

private fun NavGraphBuilder.homeNavGraph(appState: AppState) {
    navigation(
        route = MainDestinations.Home.route,
        startDestination = HomeDestinations.WhosIn.route
    ) {
        composable(route = HomeDestinations.WhosIn.route) {
            val user = (appState.loginState as? AppState.LoginState.LoggedIn)?.user ?: return@composable
            val viewModel: WhosInViewModel = getViewModel(parameters = { parametersOf(user) })
            WhosInScreen(viewModel)
        }
        composable(HomeDestinations.Team.route) {
            val user = (appState.loginState as? AppState.LoginState.LoggedIn)?.user ?: return@composable
            val viewModel: TeamViewModel = getViewModel(parameters = { parametersOf(user) })
            TeamScreen(viewModel)
        }
        composable(HomeDestinations.Account.route) {
            val user = (appState.loginState as? AppState.LoginState.LoggedIn)?.user ?: return@composable

            AccountScreen(
                user = user,
                onLogOut = appState::setLoggedOut,
                onCreateNewTeam = { appState.navigate(OnboardingDestinations.CreateTeam.route) },
                onJoinNewTeam = { appState.navigate(OnboardingDestinations.JoinTeam.route) }
            )
        }
        composable(route = HomeDestinations.TeamInfo.route) {
            val user = (appState.loginState as? AppState.LoginState.LoggedIn)?.user ?: return@composable
            val viewModel: TeamInfoViewModel = getViewModel(parameters = { parametersOf(user) })
            TeamInfoScreen(viewModel)
        }
    }
}