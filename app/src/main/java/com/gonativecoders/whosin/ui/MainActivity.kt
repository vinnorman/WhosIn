package com.gonativecoders.whosin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gonativecoders.whosin.core.theme.WhosInTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
    }

    @Composable
    fun MainScreen(viewModel: MainViewModel = getViewModel()) {
        val uiState = viewModel.uiState

        WhosInTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                MainNavigator(
                    onLoggedIn = viewModel::setLoggedIn,
                    onLoggedOut = viewModel::setLoggedOut,
                    onAccountCreated = viewModel::onStartOnboarding,
                    uiState = uiState,
                    onUserUpdated = viewModel::onUserUpdated,
                    onOnboarding = viewModel::onStartOnboarding
                )

            }
        }
    }

}