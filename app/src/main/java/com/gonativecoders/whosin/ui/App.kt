@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.ui.navigation.AppNavigation
import com.gonativecoders.whosin.ui.navigation.BottomNavigation
import com.gonativecoders.whosin.ui.screens.login.LoginViewModel
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@Composable
fun App() {
    WhosInTheme {
        val appState = rememberAppState()

        Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background) {
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
}

val koinModules = module {
    single { AuthService() }
    single { AuthRepository(authService = get()) }

    viewModel { LoginViewModel(authRepository = get())}
}