package com.gonativecoders.whosin.ui.home.account

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class EditProfileViewModel(
    val user: User,
    private val authManager: AuthManager,
    private val storage: FirebaseStorage = Firebase.storage
) : ViewModel() {

    data class UiState(
        val displayName: String = "",
        val existingImageUri: String? = null,
        val newImageUri: String? = null,
        val changesMade: Boolean = false,
        val error: Exception? = null
    )

    var uiState by mutableStateOf(UiState(displayName = user.name, existingImageUri = user.photoUri))
        private set

    fun onNameChange(newValue: String) {
        uiState = uiState.copy(displayName = newValue, changesMade = true)
    }

    fun onImageChange(uri: String) {
        uiState = uiState.copy(newImageUri = uri, changesMade = true)
    }

    suspend fun saveChanges(): User {
        val updatedUser = if (uiState.newImageUri != null) {
            val storageRef = storage.reference
            val profilePhotoRef = storageRef.child("profile_photos/${user.id}.jpg")
            profilePhotoRef.putFile(Uri.parse(uiState.newImageUri)).await()
            user.copy(
                name = uiState.displayName,
                photoUri = profilePhotoRef.downloadUrl.await().toString()
            )
        } else {
            user.copy(
                name = uiState.displayName
            )
        }
        authManager.updateUser(updatedUser)
        return updatedUser
    }

}