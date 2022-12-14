@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.data.auth.FirebaseAuthService
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.FirestoreTeamService
import com.gonativecoders.whosin.data.team.TeamService
import com.gonativecoders.whosin.ui.composables.BottomBar
import com.gonativecoders.whosin.ui.screens.home.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.home.me.MeScreen
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.screens.login.LoginScreen
import com.gonativecoders.whosin.ui.screens.login.LoginViewModel
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamScreen
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamViewModel
import com.gonativecoders.whosin.ui.screens.onboarding.jointeam.JoinTeamScreen
import com.gonativecoders.whosin.ui.screens.onboarding.welcome.WelcomeScreen
import com.gonativecoders.whosin.ui.screens.register.RegisterScreen
import com.gonativecoders.whosin.ui.screens.register.RegisterViewModel
import com.gonativecoders.whosin.ui.screens.splash.SplashScreen
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Composable
fun App() {
    WhosInTheme {
        val appState = rememberAppState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    if (appState.isBottomNavigationRoute) {
                        BottomBar(
                            items = appState.homeDestinations,
                            currentRoute = appState.currentRoute!!,
                            onItemSelected = appState::navigateToBottomBarRoute
                        )
                    }
                },
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(
                                snackbarData = snackbarData,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    )
                },
                content = { innerPadding ->
                    NavHost(
                        navController = appState.navController,
                        startDestination = MainDestinations.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) { navGraph(appState) }
                }
            )

        }

    }
}

fun NavGraphBuilder.navGraph(appState: AppState) {
    composable(MainDestinations.Splash.route) {
        SplashScreen(navigate = appState::clearAndNavigate)
    }
    composable(MainDestinations.Login.route) {
        LoginScreen(
            navigate = { route -> appState.navigate(route) }
        )
    }
    composable(MainDestinations.Register.route) {
        RegisterScreen(
            onLoggedIn = appState::onLoggedIn,
            navigate = { route -> appState.navigateAndPopUp(route, MainDestinations.Login.route) }
        )
    }
    navigation(route = MainDestinations.Home.route, startDestination = HomeDestinations.WhosIn.route) {
        composable(HomeDestinations.WhosIn.route) { WhosInScreen() }
        composable(HomeDestinations.Me.route) { MeScreen() }
        composable(HomeDestinations.Account.route) { AccountScreen(onLoggedOut = appState::onLoggedOut) }
    }
    navigation(route = MainDestinations.Onboarding.route, startDestination = OnboardingDestinations.Welcome.route) {
        composable(OnboardingDestinations.Welcome.route) { WelcomeScreen(appState::navigate) }
        composable(OnboardingDestinations.CreateTeam.route) { CreateTeamScreen(appState::navigate) }
        composable(OnboardingDestinations.JoinTeam.route) { JoinTeamScreen() }
    }
}

sealed class MainDestinations(val route: String) {
    object Splash : MainDestinations("splash")
    object Login : MainDestinations("login")
    object Register : MainDestinations("register")
    object Home : MainDestinations("home")
    object Onboarding: MainDestinations("onboarding")
}

sealed class HomeDestinations(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object WhosIn : HomeDestinations("whosin", R.string.screen_name_whos_in, Icons.Filled.Search)
    object Me : HomeDestinations("me", R.string.screen_name_me, Icons.Filled.DateRange)
    object Account : HomeDestinations("account", R.string.screen_name_account, Icons.Filled.AccountCircle)
}

sealed class OnboardingDestinations(val route: String) {
    object Welcome : OnboardingDestinations("welcome")
    object CreateTeam : OnboardingDestinations("create-team")
    object JoinTeam : OnboardingDestinations("join-team")
}

val koinModules = module {
    viewModel { LoginViewModel(authService = get(), dataStore = get()) }
    viewModel { RegisterViewModel(authService = get()) }
    viewModel { CreateTeamViewModel(teamService = get()) }

    single<AuthService> { FirebaseAuthService() }
    single<TeamService> { FirestoreTeamService() }

    single { DataStoreRepository(androidContext()) }
}