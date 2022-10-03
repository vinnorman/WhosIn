package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

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

    fun onLoginClicked(onLoggedIn: () -> Unit) {
        authRepository.login(uiState.email, uiState.password) { error ->
            if (error == null) {
                onLoggedIn()
            } else {
                uiState = uiState.copy(error = error)
            }
        }
    }


}