package com.gonativecoders.whosin.ui.onboarding.profilesetup

import android.net.Uri
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

    suspend fun updateUser(image: Uri?): User {
        uiState = uiState.copy(saving = true)
        val photoUri = image?.let { userRepository.uploadProfilePhoto(user, image) }
        val updatedUser = user.copy(
            hasSetupProfile = true,
            photoUri = photoUri
        )
        userRepository.updateUser(updatedUser)
        return updatedUser
    }

    fun onNameUpdated(name: String) {
        uiState = uiState.copy(displayName = name)
    }

    data class UiState(
        val displayName: String,
        val imageUri: String?,
        val saving: Boolean
    )

}