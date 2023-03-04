package com.gonativecoders.whosin.data.auth

import com.google.firebase.auth.FirebaseUser

class AuthRepository(private val service: AuthService) {

    suspend fun login(email: String, password: String): FirebaseUser {
        return service.login(email, password)
    }

    suspend fun createAccount(email: String, password: String, displayName: String) {
        return service.createAccount(email, password, displayName)
    }

}