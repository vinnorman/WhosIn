package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository, private val dataStore: DataStoreRepository) : ViewModel() {

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

    fun onLoginClicked(onLoggedIn: (user: User) -> Unit) {
        viewModelScope.launch {
            try {
                val user = repository.login(uiState.email, uiState.password)
                onLoggedIn(user)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

}