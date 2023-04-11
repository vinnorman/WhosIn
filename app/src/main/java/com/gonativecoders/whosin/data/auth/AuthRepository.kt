package com.gonativecoders.whosin.data.auth

import com.gonativecoders.whosin.data.auth.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository(private val service: AuthService) {


    suspend fun login(email: String, password: String): User {
        return service.login(email, password)
    }

    suspend fun getUserDetails(userId: String): User {
        return service.getUser(userId)
    }

    suspend fun createAccount(email: String, password: String, displayName: String): User {
        return service.createAccount(email, password, displayName)
    }

    suspend fun getCurrentUser(): User {
        val user = Firebase.auth.currentUser ?: throw Exception("No logged in user!")
        return service.getUser(user.uid)
    }


}