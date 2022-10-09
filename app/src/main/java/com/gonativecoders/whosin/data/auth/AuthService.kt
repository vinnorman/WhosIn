package com.gonativecoders.whosin.data.auth

interface AuthService {

    fun login(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun createAccount(email: String, password: String, displayName: String, onResult: (Throwable?) -> Unit)
}