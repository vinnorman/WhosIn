package com.gonativecoders.whosin.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthService

class RegisterViewModel(private val authService: AuthService) : ViewModel() {

    data class UiState(
        val displayName: String = "",
        val email: String = "",
        val password: String = "",
        val isCreatingAccount: Boolean = false,
        val error: Throwable? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onNameChange(newValue: String) {
        uiState = uiState.copy(displayName = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun onCreateAccountClicked(onLoggedIn: () -> Unit) {
        authService.createAccount(uiState.email, uiState.password, uiState.displayName) { error ->
            if (error == null) {
                onLoggedIn()
            } else {
                uiState = uiState.copy(error = error)
            }
        }
    }

}