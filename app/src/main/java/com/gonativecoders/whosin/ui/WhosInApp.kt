@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gonativecoders.whosin.ui.navigation.BottomNavigation
import com.gonativecoders.whosin.ui.navigation.Screen
import com.gonativecoders.whosin.ui.screens.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.login.LoginScreen
import com.gonativecoders.whosin.ui.screens.me.MeScreen
import com.gonativecoders.whosin.ui.screens.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun WhosInApp() {
    WhosInTheme {
        val appState = rememberAppState()

        Scaffold(
            bottomBar = { if (appState.isBottomNavigationRoute) BottomNavigation(appState.navController) },
            content = { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Screen.Login.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Login.route) {
                        LoginScreen(onLoggedIn = {
                            appState.navController.navigate(Screen.BottomNavScreen.WhosIn.route) {
                                popUpTo(appState.navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                                    inclusive = true
                                }
                            }
                        })
                    }
                    composable(Screen.BottomNavScreen.WhosIn.route) { WhosInScreen() }
                    composable(Screen.BottomNavScreen.Me.route) { MeScreen() }
                    composable(Screen.BottomNavScreen.Account.route) { AccountScreen() }
                }
            }
        )

    }
}