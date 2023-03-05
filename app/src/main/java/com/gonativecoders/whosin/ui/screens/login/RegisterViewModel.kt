package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

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

    fun onCreateAccountClicked(onLoggedIn: (User) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.createAccount(uiState.email, uiState.password, uiState.displayName)
                onLoggedIn(user)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

}