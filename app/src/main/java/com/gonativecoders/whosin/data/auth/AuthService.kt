package com.gonativecoders.whosin.data.auth

import com.gonativecoders.whosin.data.auth.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthService(private val database: FirebaseFirestore = Firebase.firestore, private val auth: FirebaseAuth = Firebase.auth) {


    suspend fun createAccount(email: String, password: String, displayName: String): User {
        val user = auth.createUserWithEmailAndPassword(email, password).await().user ?: throw Exception("Couldn't create account")
        user.updateProfile(userProfileChangeRequest {
            this.displayName = displayName
        }).await()
        return saveUser(user, displayName)
    }

    private suspend fun saveUser(user: FirebaseUser, displayName: String): User {
        database.collection("users")
            .document(user.uid)
            .set(mapOf("name" to displayName)).await()
        return User(name = displayName).apply { id = user.uid }
    }

    suspend fun login(email: String, password: String): User {
        val firebaseUser = auth.signInWithEmailAndPassword(email, password).await().user ?: throw Exception("No user found")
        return database.collection("users").document(firebaseUser.uid).get().await().toObject() ?: throw Exception("No user found")
    }

    suspend fun getUser(userId: String): User  {
        return database.collection("users").document(userId).get().await().toObject() ?: throw Exception("No user found")
    }

}