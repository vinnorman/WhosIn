package com.gonativecoders.whosin.ui.splash

import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel(private val repository: AuthRepository, private val dataStoreRepository: DataStoreRepository) : ViewModel() {

    suspend fun getLoginStatus(): LoginStatus {
        val firebaseUser = Firebase.auth.currentUser ?: return LoginStatus.LoggedOut
        val user = repository.getUserDetails(firebaseUser.uid)

        return if (dataStoreRepository.getBoolean(DataStoreRepository.HAS_COMPLETED_ONBOARDING)) {
            LoginStatus.LoggedIn(user)
        } else {
            LoginStatus.Onboarding(user)
        }
    }

    sealed class LoginStatus {

        data class LoggedIn(val user: User) : LoginStatus()

        data class Onboarding(val user: User) : LoginStatus()

        object LoggedOut : LoginStatus()

    }

}