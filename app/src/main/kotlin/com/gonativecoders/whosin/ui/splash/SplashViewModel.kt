package com.gonativecoders.whosin.ui.splash

import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User

class SplashViewModel(private val authManager: AuthManager) : ViewModel() {

    suspend fun getLoginStatus(): LoginStatus {
        val user = authManager.getCurrentUser() ?: return LoginStatus.LoggedOut
        return LoginStatus.LoggedIn(user)
    }

    sealed class LoginStatus {

        data class LoggedIn(val user: User) : LoginStatus()

        object LoggedOut : LoginStatus()

    }

}