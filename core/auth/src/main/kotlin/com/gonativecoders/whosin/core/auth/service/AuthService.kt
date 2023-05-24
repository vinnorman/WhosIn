package com.gonativecoders.whosin.core.auth.service

import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.auth.service.model.FirebaseUser
import com.gonativecoders.whosin.core.auth.service.model.toUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

internal class AuthService(private val firebaseAuth: FirebaseAuth = Firebase.auth, private val firestore: FirebaseFirestore = Firebase.firestore) {

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
        return firestore.collection("users").document(user.uid).get().await().toObject<FirebaseUser>()?.toUser() ?: throw Exception("No user found")
    }

    suspend fun getCurrentUser(): User? {
        val userId = Firebase.auth.currentUser?.uid ?: return null
        return firestore.collection("users").document(userId).get().await().toObject<FirebaseUser>()?.toUser()
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

    fun logOut() {
        Firebase.auth.signOut()
    }
}