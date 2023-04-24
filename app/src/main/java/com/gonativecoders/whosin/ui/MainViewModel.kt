package com.gonativecoders.whosin.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Splash)
        private set

    fun setLoggedIn(user: User) {
        uiState = UiState.LoggedIn(user)
    }

    fun setLoggedOut() {
        Firebase.auth.signOut()
        uiState = UiState.LoggedOut
    }

    fun onUserUpdated(user: User) {
        uiState = UiState.LoggedIn(user)
    }

    sealed class UiState {

        object Splash : UiState()
        object LoggedOut : UiState()
        data class LoggedIn(var user: User) : UiState()

    }

}