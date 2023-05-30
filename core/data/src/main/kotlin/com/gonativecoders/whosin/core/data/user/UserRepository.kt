package com.gonativecoders.whosin.core.data.user

import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.user.service.UserService

class UserRepository(private val service: UserService) {

    suspend fun uploadProfilePhoto(user: User, image: ByteArray) {
        service.uploadProfilePhoto(user, image)
    }

    suspend fun setOnboardingComplete(user: User) {
        service.updateUser(user.copy(hasSetupProfile = true))
    }

    suspend fun updateUser(user: User) {
        service.updateUser(user)
    }

}