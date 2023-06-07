package com.gonativecoders.whosin.core.data.user.service

import android.net.Uri
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.auth.service.model.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

internal class UserService(
    private val storage: FirebaseStorage = Firebase.storage,
    private val firestore: FirebaseFirestore = Firebase.firestore
) {

    suspend fun uploadProfilePhoto(user: User, image: Uri): String {
        val profilePhotoRef = storage.reference.child("profile_photos/${user.id}.jpg")
        profilePhotoRef.putFile(image).await()
        return profilePhotoRef.downloadUrl.await().toString()
    }

    suspend fun updateUser(user: User) {
        val firebaseUser = FirebaseUser.parse(user)
        firestore.runTransaction {
            firestore.collection("users")
                .document(firebaseUser.id)
                .set(firebaseUser)
            firebaseUser.teams.forEach { teamId ->
                firestore.collection("teams").document(teamId).collection("members").document(firebaseUser.id).set(firebaseUser)
            }
        }.await()
    }

}