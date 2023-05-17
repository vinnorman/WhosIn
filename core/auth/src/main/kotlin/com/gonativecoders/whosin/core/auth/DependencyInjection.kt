package com.gonativecoders.whosin.core.auth

import com.gonativecoders.whosin.core.auth.service.AuthService
import org.koin.dsl.module

val authModule = module {

    factory { AuthManager(service = get()) }

    single { AuthService() }

}