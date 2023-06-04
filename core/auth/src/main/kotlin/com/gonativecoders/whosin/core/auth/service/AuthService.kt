package com.gonativecoders.whosin.core.auth.service

import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.auth.service.model.FirebaseUser
import com.gonativecoders.whosin.core.auth.service.model.toUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

internal class AuthService(
    private val firebaseAuth: FirebaseAuth = Firebase.auth,
    private val firestore: FirebaseFirestore = Firebase.firestore
) {

    suspend fun createAccount(email: String, password: String, displayName: String): User {
        val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user ?: throw Exception("Couldn't create account")
        user.updateProfile(userProfileChangeRequest { this.displayName = displayName }).await()

        firestore.collection("users")
            .document(user.uid)
            .set(FirebaseUser(name = displayName, email = email)).await()

        return User(id = user.uid, name = displayName, email = email, hasSetupProfile = false)
    }

    suspend fun login(email: String, password: String): User {
        val user = firebaseAuth.signInWithEmailAndPassword(email, password).await().user ?: throw Exception("No user found")
        return firestore.collection("users").document(user.uid).get().await().toObject<FirebaseUser>()?.toUser()
            ?: throw Exception("No user found")
    }

    suspend fun getCurrentUser(): User? {
        val userId = firebaseAuth.currentUser?.uid ?: return null
        return firestore.collection("users").document(userId).get().await().toObject<FirebaseUser>()?.toUser()
    }

    suspend fun signInWithGoogle(idToken: String, email: String, displayName: String): User {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        val user = firebaseAuth.signInWithCredential(firebaseCredential).await().user ?: throw Exception("Couldn't create account")

        firestore.collection("users")
            .document(user.uid)
            .set(
                mapOf(
                    "name" to displayName,
                    "email" to email
                ), SetOptions.merge()
            ).await()

        return firestore.collection("users")
            .document(user.uid).get().await().toObject<FirebaseUser>()!!.toUser()
    }

    fun logOut() {
        Firebase.auth.signOut()
    }

}