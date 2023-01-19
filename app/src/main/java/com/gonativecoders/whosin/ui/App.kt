@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.gonativecoders.whosin.ui.composables.BottomBar
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.gonativecoders.whosin.ui.navigation.navGraph
import com.gonativecoders.whosin.ui.theme.WhosInTheme

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