package com.gonativecoders.whosin.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.auth.AuthManager
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.launch

class LoginViewModel(private val authManager: AuthManager) : ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val isLoggingIn: Boolean = false,
        val error: Exception? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    fun onPasswordChanged(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun onLoginClicked(onLoggedIn: (user: com.gonativecoders.whosin.core.auth.model.User) -> Unit) {
        uiState = uiState.copy(isLoggingIn = true)
        viewModelScope.launch {
            try {
                val user = authManager.login(uiState.email, uiState.password)
                onLoggedIn(user)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception, isLoggingIn = false)
            }
        }
    }

    fun onErrorDialogDismissed() {
        uiState = uiState.copy(error = null)
    }

    fun onGoogleSign(credential: SignInCredential, onLoggedIn: (user: com.gonativecoders.whosin.core.auth.model.User) -> Unit) {
        viewModelScope.launch {
            try {
                val user = authManager.signInWithGoogle(credential.googleIdToken!!, credential.id, credential.displayName!!)
                onLoggedIn(user)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception, isLoggingIn = false)
            }
        }
    }

}