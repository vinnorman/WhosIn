package com.gonativecoders.whosin.data.auth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthService {

    suspend fun createAccount(email: String, password: String, displayName: String) {
        val user = Firebase.auth.createUserWithEmailAndPassword(email, password).await().user ?: throw Exception("Couldn't create account")
        user.updateProfile(userProfileChangeRequest {
            this.displayName = displayName
        }).await()
        saveUser(user, displayName)
    }

    private suspend fun saveUser(user: FirebaseUser, displayName: String) {
        Firebase.firestore.collection("users")
            .document(user.uid)
            .set(mapOf("name" to displayName)).await()
    }

    suspend fun login(email: String, password: String): FirebaseUser {
        return Firebase.auth.signInWithEmailAndPassword(email, password).await().user ?: throw Exception("No user found")
    }

}