@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.ui.composables.BottomBar
import com.gonativecoders.whosin.ui.navigation.AppNavigation
import com.gonativecoders.whosin.ui.navigation.HomeDestinations
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.gonativecoders.whosin.ui.theme.WhosInTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }

}

@Composable
fun MainContent() {
    WhosInTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppScaffold()
        }
    }
}

@Composable
fun AppScaffold() {
    val appState = rememberAppState()
    Scaffold(
        bottomBar = {
            if (appState.isBottomNavigationRoute) {
                BottomBar(
                    items = appState.homeDestinations,
                    currentRoute = appState.currentRoute!!,
                    navController = appState.navController
                )
            }
        },
        topBar = {
            val loginState = appState.loginState
            if (appState.isBottomNavigationRoute) {
                CenterAlignedTopAppBar(
                    title = {
                        if (loginState is AppState.LoginState.LoggedIn) {
                            Text(text = loginState.user.team?.name ?: "Who's In?")
                        }
                    },
                    actions = {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = "Go to Current Week",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = {
                            appState.navigate(HomeDestinations.TeamInfo.route)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = "Account Button",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
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
            AppNavigation(
                appState = appState,
                startDestination = MainDestinations.Splash.route,
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}