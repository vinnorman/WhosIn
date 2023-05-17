package com.gonativecoders.whosin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User

class MainViewModel(private val authManager: AuthManager) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Splash)
        private set

    fun setLoggedIn(user: User) {
        uiState = UiState.LoggedIn(user)
    }

    fun setLoggedOut() {
        authManager.logOut()
        uiState = UiState.LoggedOut
    }

    sealed class UiState {

        object Splash : UiState()
        object LoggedOut : UiState()

        data class LoggedIn(var user: User) : UiState() {

            override fun equals(other: Any?): Boolean {
                return super.equals(other)
            }

            override fun hashCode(): Int {
                return user.hashCode()
            }

        }

    }

}