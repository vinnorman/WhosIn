package com.gonativecoders.whosin.ui.home.onboarding.profilesetup

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class ProfileSetupViewModel(
    val user: User,
    private val authRepository: AuthRepository,
    private val storage: FirebaseStorage = Firebase.storage
) : ViewModel() {

    suspend fun updateUser(imageUri: Uri) {
        val storageRef = storage.reference
        val profilePhotoRef = storageRef.child("profile_photos/${user.id}.jpg")
        profilePhotoRef.putFile(imageUri).await()
        user.photoUri = profilePhotoRef.downloadUrl.await().toString()
        authRepository.updateUser(user.apply { hasSetupProfile = true })
    }

    suspend fun markOnboardingComplete() {
        authRepository.updateUser(user.apply { hasSetupProfile = true })
    }

}