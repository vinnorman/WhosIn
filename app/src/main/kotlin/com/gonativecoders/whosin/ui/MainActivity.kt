package com.gonativecoders.whosin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.core.util.isFreshInstall
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.uiState is MainViewModel.UiState.Splash }
        setContent { MainScreen() }
    }

    @Composable
    fun MainScreen() {
        val uiState = viewModel.uiState

        val navController: NavHostController = rememberNavController()

        val context = LocalContext.current

        WhosInTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                MainNavHost(
                    navController = navController,
                    onLoggedIn = viewModel::setLoggedIn,
                    onUserUpdated = viewModel::setLoggedIn,
                    onLoggedOut = viewModel::setLoggedOut,
                    uiState = uiState,
                    onUserLeftTeam = viewModel::fetchUser,
                )

            }
        }

        when (uiState) {
            MainViewModel.UiState.Splash -> navController.navigate(MainDestinations.Splash)
            
            is MainViewModel.UiState.LoggedIn -> {
                if (!uiState.user.hasSetupProfile) return navController.navigate(MainDestinations.ProfileSetup, clear = true)
                if (uiState.user.currentTeamId == null) return navController.navigate(MainDestinations.Welcome, clear = true)
                navController.navigate(destination = MainDestinations.Home, clear = true)
            }

            MainViewModel.UiState.LoggedOut -> {
                if (context.isFreshInstall) return navController.navigate(destination = MainDestinations.CreateAccount, clear = true)
                navController.navigate(destination = MainDestinations.Login, clear = true)
            }
        }


    }

}