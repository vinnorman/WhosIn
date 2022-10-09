package com.gonativecoders.whosin.data.team

import com.gonativecoders.whosin.util.getRandomString
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirestoreTeamService : TeamService {

    override fun createTeam(teamName: String, onResult: (Throwable?) -> Unit) {
        val user = Firebase.auth.currentUser ?: return
        Firebase.firestore.collection("teams")
            .add(mapOf(
                "name" to teamName,
                "createdBy" to user.uid,
                "teamCode" to getRandomString(6)
            ))
            .addOnCompleteListener {
                onResult(it.exception)
            }
    }

}