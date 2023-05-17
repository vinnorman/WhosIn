package com.gonativecoders.whosin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gonativecoders.whosin.core.theme.WhosInTheme
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

        WhosInTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                MainNavigator(
                    onLoggedIn = viewModel::setLoggedIn,
                    onUserUpdated = viewModel::setLoggedIn,
                    onLoggedOut = viewModel::setLoggedOut,
                    uiState = uiState
                )

            }
        }
    }

}