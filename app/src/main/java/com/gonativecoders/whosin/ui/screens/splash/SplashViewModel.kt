package com.gonativecoders.whosin.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel(private val repository: AuthRepository) : ViewModel() {

    suspend fun getLoginStatus() : LoginStatus {
        val firebaseUser = Firebase.auth.currentUser ?: return LoginStatus.LoggedOut
        val user = repository.getUserDetails(firebaseUser.uid)
        return LoginStatus.LoggedIn(user)
    }

    suspend fun getUserDetails(userId: String): User {
        return repository.getUserDetails(userId)
    }

    sealed class LoginStatus {

        data class LoggedIn(val user: User) : LoginStatus()

        object LoggedOut : LoginStatus()

    }

}