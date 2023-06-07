package com.gonativecoders.whosin.ui.home.account

import android.net.Uri
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
        val newImageUri: Uri? = null,
        val changesMade: Boolean = false,
        val error: Exception? = null,
        val saving: Boolean = false
    )

    var uiState by mutableStateOf(UiState(displayName = user.name, existingImageUri = user.photoUri))
        private set

    fun onNameChange(newValue: String) {
        uiState = uiState.copy(displayName = newValue, changesMade = true)
    }

    fun onImageChange(uri: Uri) {
        uiState = uiState.copy(newImageUri = uri, changesMade = true)
    }

    suspend fun saveChanges(): User {
        uiState = uiState.copy(saving = true)
        val newImageUri = uiState.newImageUri
        val updatedUser = if (newImageUri != null) {
            val photoUri = userRepository.uploadProfilePhoto(user, newImageUri)
            user.copy(
                photoUri = photoUri,
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