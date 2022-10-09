package com.gonativecoders.whosin.data.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseAuthService : AuthService {

    override fun createAccount(email: String, password: String, displayName: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.exception == null) {
                    addDisplayName(displayName, onResult)
                    Firebase.firestore.collection("users").add(mapOf("name" to displayName, "id" to it.result.user?.uid))
                } else {
                    onResult(it.exception)
                }
            }
    }

    private fun addDisplayName(displayName: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.currentUser!!.updateProfile(userProfileChangeRequest {
            this.displayName = displayName
        }).addOnCompleteListener {
            onResult(it.exception)
        }
    }

    override fun login(email: String, password: String, onResult: (Throwable?) -> Unit) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { onResult(it.exception) }
        } catch (exception: IllegalArgumentException) {
            onResult(exception)
        }
    }

}