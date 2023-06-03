package com.gonativecoders.whosin.ui.onboarding.profilesetup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.user.UserRepository

class ProfileSetupViewModel(
    val user: User,
    private val userRepository: UserRepository,
) : ViewModel() {

    var uiState: UiState by mutableStateOf(
        UiState(
            displayName = user.name,
            imageUri = user.photoUri,
            saving = false
        )
    )
        private set

    suspend fun updateUser(imageUri: String, image: ByteArray): User {
        uiState = uiState.copy(saving = true)
        userRepository.uploadProfilePhoto(user, image)
        return user.copy(
            hasSetupProfile = true,
            photoUri = imageUri
        )
    }

    fun onNameUpdated(name: String) {
        uiState = uiState.copy(displayName = name)
    }

    suspend fun markOnboardingComplete(): User {
        uiState = uiState.copy(saving = true)
        userRepository.setOnboardingComplete(user)
        return user.copy(
            hasSetupProfile = true
        )
    }

    data class UiState(
        val displayName: String,
        val imageUri: String?,
        val saving: Boolean
    )

}