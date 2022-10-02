package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val isLoggingIn: Boolean = false,
        val error: Throwable? = null
    )

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    var uiState: MutableState<UiState> = mutableStateOf(UiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChanged(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLoginClicked(onLoginResult: (isSuccessful: Boolean) -> Unit) {
        authRepository.login(email, password) { error ->
            if (error == null) {
                onLoginResult(true)
            }
        }
    }


}