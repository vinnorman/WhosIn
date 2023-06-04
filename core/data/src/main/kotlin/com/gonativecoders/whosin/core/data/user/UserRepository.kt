package com.gonativecoders.whosin.core.data.user

import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.user.service.UserService

class UserRepository internal constructor(private val service: UserService) {

    suspend fun uploadProfilePhoto(user: User, image: ByteArray): String {
        return service.uploadProfilePhoto(user, image)
    }

    suspend fun updateUser(user: User) {
        service.updateUser(user)
    }

}