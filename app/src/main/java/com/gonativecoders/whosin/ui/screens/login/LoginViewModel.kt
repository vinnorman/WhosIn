package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import kotlinx.coroutines.launch

class LoginViewModel(private val authService: AuthService, private val dataStore: DataStoreRepository) : ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val isLoggingIn: Boolean = false,
        val error: Throwable? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun onLoginClicked(navigate: (route: String) -> Unit) {
        authService.login(uiState.email, uiState.password) { error ->
            if (error == null) {
                onLoggedIn(navigate)
            } else {
                uiState = uiState.copy(error = error)
            }
        }
    }

    fun onLoggedIn(navigate: (route: String) -> Unit) {
        viewModelScope.launch {
            if (dataStore.getBoolean("has-seen-onboarding")) {
                navigate(MainDestinations.Home.route)
            } else {
                navigate(MainDestinations.Onboarding.route)
            }
        }
    }

}