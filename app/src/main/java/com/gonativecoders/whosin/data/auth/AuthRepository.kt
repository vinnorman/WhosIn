package com.gonativecoders.whosin.data.auth

import com.gonativecoders.whosin.data.auth.model.User

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


}