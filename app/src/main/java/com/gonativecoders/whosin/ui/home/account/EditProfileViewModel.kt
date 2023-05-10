package com.gonativecoders.whosin.ui.home.account

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class EditProfileViewModel(
    val user: User,
    private val authRepository: AuthRepository,
    private val storage: FirebaseStorage = Firebase.storage
    ) : ViewModel() {

    data class UiState(
        val displayName: String = "",
        val imageUri: String? = null,
        val changesMade: Boolean = false,
        val error: Exception? = null
    )

    var uiState by mutableStateOf(UiState(displayName = user.name, imageUri = user.photoUri))
        private set

    fun onNameChange(newValue: String) {
        uiState = uiState.copy(displayName = newValue, changesMade = true)
    }

    fun onImageChange(uri: String) {
        uiState = uiState.copy(imageUri = uri, changesMade = true)
    }

    suspend fun saveChanges(): User {
        uiState.imageUri?.let {uri ->
            val storageRef = storage.reference
            val profilePhotoRef = storageRef.child("profile_photos/${user.id}.jpg")
            profilePhotoRef.putFile(Uri.parse(uri)).await()
            user.photoUri = profilePhotoRef.downloadUrl.await().toString()
        }
        val updatedUser = user.copy(name = uiState.displayName).apply { id = user.id }
        authRepository.updateUser(updatedUser)
        return updatedUser
    }




}