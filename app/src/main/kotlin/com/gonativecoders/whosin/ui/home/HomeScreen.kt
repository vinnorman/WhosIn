package com.gonativecoders.whosin.ui.home

import androidx.compose.runtime.Composable
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.components.Loading
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = getViewModel(),
    onLoggedOut: () -> Unit,
    onUserUpdated: (User) -> Unit
) {

    when (val uiState = viewModel.uiState) {
        HomeViewModel.UiState.Loading -> Loading()
        is HomeViewModel.UiState.Success -> {
            HomeNavigator(
                user = uiState.user,
                team = uiState.team,
                onUserUpdated = onUserUpdated,
                onLoggedOut = onLoggedOut
            )
        }
        HomeViewModel.UiState.LoggedOut -> onLoggedOut()
    }

}