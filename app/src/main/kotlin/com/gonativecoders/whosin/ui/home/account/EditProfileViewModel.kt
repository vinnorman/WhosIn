package com.gonativecoders.whosin.ui.home.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.user.UserRepository

class EditProfileViewModel(
    val user: User,
    private val userRepository: UserRepository
) : ViewModel() {

    data class UiState(
        val displayName: String = "",
        val existingImageUri: String? = null,
        val newImageUri: String? = null,
        val changesMade: Boolean = false,
        val error: Exception? = null,
        val saving: Boolean = false
    )

    var uiState by mutableStateOf(UiState(displayName = user.name, existingImageUri = user.photoUri))
        private set

    fun onNameChange(newValue: String) {
        uiState = uiState.copy(displayName = newValue, changesMade = true)
    }

    fun onImageChange(uri: String) {
        uiState = uiState.copy(newImageUri = uri, changesMade = true)
    }

    suspend fun saveChanges(image: ByteArray?): User {
        uiState = uiState.copy(saving = true)
        val updatedUser = if (image != null) {
            userRepository.uploadProfilePhoto(user, image)
            user.copy(
                photoUri = uiState.newImageUri,
                name = uiState.displayName
            )
        } else {
            user.copy(
                name = uiState.displayName
            )
        }
        userRepository.updateUser(updatedUser)
        return updatedUser
    }

}