package com.gonativecoders.whosin.ui.home.onboarding.profilesetup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileSetupViewModel(
    private val user: User,
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val storage: FirebaseStorage = Firebase.storage
) : ViewModel() {

    fun onNextClicked(imageUri: Uri, onSuccess: (User) -> Unit) {
        viewModelScope.launch {
            val storageRef = storage.reference

            val profilePhotoRef = storageRef.child("profile_photos/${user.id}.jpg")
             profilePhotoRef.putFile(imageUri).await()
            user.photoUri = profilePhotoRef.downloadUrl.await().toString()
            authRepository.updateUser(user.apply { hasCompletedOnboarding = true })

            dataStoreRepository.putBoolean(DataStoreRepository.HAS_COMPLETED_ONBOARDING, true)
            onSuccess(user)
        }
    }

}