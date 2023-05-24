package com.gonativecoders.whosin.ui.home.onboarding.profilesetup

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class ProfileSetupViewModel(
    val user: User,
    private val authManager: AuthManager,
    private val storage: FirebaseStorage = Firebase.storage
) : ViewModel() {

    suspend fun updateUser(imageUri: Uri) {
        val storageRef = storage.reference
        val profilePhotoRef = storageRef.child("profile_photos/${user.id}.jpg")
        profilePhotoRef.putFile(imageUri).await()
        authManager.updateUser(
            user.copy(
                photoUri = profilePhotoRef.downloadUrl.await().toString(),
                hasSetupProfile = true
            )
        )
    }

    suspend fun markOnboardingComplete() {
        authManager.updateUser(user.copy(hasSetupProfile = true))
    }

}