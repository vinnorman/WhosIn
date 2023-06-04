package com.gonativecoders.whosin.core.auth

import com.gonativecoders.whosin.core.auth.exceptions.AuthException
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.auth.service.AuthService
import com.google.firebase.auth.FirebaseAuthException

class AuthManager internal constructor(private val service: AuthService) {

    suspend fun createAccount(email: String, password: String, displayName: String): User = try {
        service.createAccount(email, password, displayName)
    } catch (exception: FirebaseAuthException) {
        throw AuthException(exception.message, exception)
    }

    suspend fun login(email: String, password: String): User = try {
        service.login(email, password)
    } catch (exception: FirebaseAuthException) {
        throw AuthException(exception.message, exception)
    }

    suspend fun getCurrentUser(): User? = service.getCurrentUser()

    fun logOut() = service.logOut()

    suspend fun signInWithGoogle(idToken: String, email: String, displayName: String) = try {
        service.signInWithGoogle(idToken, email, displayName)
    } catch (exception: Exception) {
        throw AuthException(exception.message, exception)
    }


}