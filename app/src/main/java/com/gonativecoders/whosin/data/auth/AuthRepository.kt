package com.gonativecoders.whosin.data.auth

class AuthRepository(private val authService: AuthService) {

    fun login(email: String, password: String, onResult: (Throwable?) -> Unit) {
        authService.login(email, password, onResult)
    }

    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        authService.createAccount(email, password, onResult)
    }

}