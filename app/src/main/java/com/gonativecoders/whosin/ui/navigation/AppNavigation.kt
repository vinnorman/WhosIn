package com.gonativecoders.whosin.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.ui.screens.home.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.home.me.MeScreen
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.screens.login.LoginScreen

sealed class MainDestinations(val route: String) {
    object Login : MainDestinations("login")
    object Home : MainDestinations("home")
}

sealed class HomeDestinations(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object WhosIn : HomeDestinations("whosin", R.string.screen_name_whos_in, Icons.Filled.Search)
    object Me : HomeDestinations("me", R.string.screen_name_me, Icons.Filled.DateRange)
    object Account : HomeDestinations("account", R.string.screen_name_account, Icons.Filled.AccountCircle)
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onLoggedIn: () -> Unit,
    onLoggedOut: () -> Unit,
    startDestination: String = MainDestinations.Login.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.Login.route) { LoginScreen(onLoggedIn = onLoggedIn) }
        navigation(route = MainDestinations.Home.route, startDestination = HomeDestinations.WhosIn.route) {
            composable(HomeDestinations.WhosIn.route) { WhosInScreen() }
            composable(HomeDestinations.Me.route) { MeScreen() }
            composable(HomeDestinations.Account.route) { AccountScreen(onLoggedOut = onLoggedOut) }
        }

    }
}