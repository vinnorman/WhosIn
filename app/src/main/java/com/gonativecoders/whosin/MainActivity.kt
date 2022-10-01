@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.ui.navigation.BottomNavigation
import com.gonativecoders.whosin.ui.navigation.Screen
import com.gonativecoders.whosin.ui.screens.account.AccountScreen
import com.gonativecoders.whosin.ui.screens.login.LoginScreen
import com.gonativecoders.whosin.ui.screens.me.MeScreen
import com.gonativecoders.whosin.ui.screens.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.theme.WhosInTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhosInTheme {

                val navController = rememberNavController()
                var isBottomNavDestination by rememberSaveable { mutableStateOf(true) }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                isBottomNavDestination = when (navBackStackEntry?.destination?.route) {
                    Screen.Login.route -> false
                    else -> true
                }
                Scaffold(
                    bottomBar = { if (isBottomNavDestination) BottomNavigation(navController) },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Login.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(Screen.Login.route) {
                                LoginScreen(onLoggedIn = { navController.navigate(Screen.WhosIn.route) })
                            }
                            composable(Screen.WhosIn.route) { WhosInScreen() }
                            composable(Screen.Me.route) { MeScreen() }
                            composable(Screen.Account.route) { AccountScreen() }
                        }
                    }
                )

            }

        }
    }
}