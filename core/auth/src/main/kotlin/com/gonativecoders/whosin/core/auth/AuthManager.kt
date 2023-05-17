package com.gonativecoders.whosin.core.auth

import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.auth.service.AuthService

class AuthManager internal constructor(private val service: AuthService) {

    suspend fun createAccount(email: String, password: String, displayName: String): User {
        return service.createAccount(email, password, displayName)
    }

    suspend fun login(email: String, password: String): User {
        return service.login(email, password)
    }

    suspend fun getCurrentUser(): User? {
        return service.getCurrentUser()
    }

    suspend fun updateUser(user: User) {
        return service.updateUser(user)
    }

}