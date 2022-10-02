@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gonativecoders.whosin.ui.navigation.AppNavigation
import com.gonativecoders.whosin.ui.navigation.BottomNavigation
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun App() {
    WhosInTheme {
        val appState = rememberAppState()

        Scaffold(
            bottomBar = {
                if (appState.isBottomNavigationRoute) {
                    BottomNavigation(
                        items = appState.homeDestinations,
                        currentRoute = appState.currentRoute!!,
                        onItemSelected = appState::onBottomNavigationSelected
                    )
                }
            },
            content = { innerPadding ->
                AppNavigation(
                    navController = appState.navController,
                    modifier = Modifier.padding(innerPadding),
                    onLoggedIn = appState::onLoggedIn,
                    onLoggedOut = appState::onLoggedOut
                )
            }
        )

    }
}