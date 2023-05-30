package com.gonativecoders.whosin.ui.onboarding.profilesetup

import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.user.UserRepository


class ProfileSetupViewModel(
    val user: User,
    private val userRepository: UserRepository,
) : ViewModel() {

    suspend fun updateUser(imageUri: String, image: ByteArray): User {
        userRepository.uploadProfilePhoto(user, image)
        return user.copy(
            hasSetupProfile = true,
            photoUri = imageUri
        )
    }

    suspend fun markOnboardingComplete(): User {
        userRepository.setOnboardingComplete(user)
        return user.copy(
            hasSetupProfile = true
        )
    }

}