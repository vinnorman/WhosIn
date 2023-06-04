package com.gonativecoders.whosin.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.launch

class CreateAccountViewModel(private val authManager: AuthManager) : ViewModel() {

    data class UiState(
        val displayName: String = "",
        val email: String = "",
        val password: String = "",
        val isCreatingAccount: Boolean = false,
        val error: Exception? = null
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
        uiState = uiState.copy(isCreatingAccount = true)
        viewModelScope.launch {
            try {
                val user = authManager.createAccount(uiState.email, uiState.password, uiState.displayName)
                onLoggedIn(user)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

    fun onGoogleSign(credential: SignInCredential, onLoggedIn: (user: User) -> Unit) {
        uiState = uiState.copy(isCreatingAccount = true)
        viewModelScope.launch {
            try {
                val user = authManager.signInWithGoogle(credential.googleIdToken!!, credential.id, credential.displayName!!)
                onLoggedIn(user)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception, isCreatingAccount = false)
            }
        }
    }

    fun onErrorDialogDismissed() {
        uiState = uiState.copy(error = null)

    }

}