package com.gonativecoders.whosin.core.data.team.service

import com.gonativecoders.whosin.core.data.team.service.model.FirebaseTeam
import com.gonativecoders.whosin.core.data.team.service.model.FirebaseTeamMember
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

internal class TeamService(private val firestore: FirebaseFirestore = Firebase.firestore) {

    suspend fun isTeamIdAvailable(teamId: String): Boolean {
        return !firestore.collection("teams").document(teamId).get().await().exists()
    }

    suspend fun createTeam(userId: String, name: String, teamId: String): FirebaseTeam {
        val user: FirebaseTeamMember = firestore.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team = FirebaseTeam(
            name = name,
            createdBy = userId
        )
        firestore.collection("teams").document(teamId).set(team).await()
        team.id = teamId
        addTeamToUser(userId = user.id, team = team)
        addUserToTeam(user, team.id)
        return team
    }

    private suspend fun addTeamToUser(userId: String, team: FirebaseTeam) {
        firestore.collection("users").document(userId)
            .update(
                "team", mapOf(
                    "id" to team.id,
                    "name" to team.name,
                )
            ).await()
    }

    private suspend fun addUserToTeam(user: FirebaseTeamMember, teamId: String) {
        firestore
            .collection("teams")
            .document(teamId)
            .collection("members")
            .document(user.id).set(user)
            .await()
    }

    suspend fun joinTeam(userId: String, teamId: String): FirebaseTeam {
        val user: FirebaseTeamMember = firestore.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team: FirebaseTeam = firestore.collection("teams").document(teamId).get().await().toObject() ?: throw Exception("Team not found")
        addTeamToUser(userId, team)
        addUserToTeam(user, team.id)
        return team
    }

    suspend fun getTeam(teamId: String): FirebaseTeam {
        return firestore.collection("teams").document(teamId).get().await().toObject() ?: throw Exception("Team not found")

    }

    suspend fun getTeamMembers(teamId: String): List<FirebaseTeamMember> {
        return firestore.collection("teams").document(teamId).collection("members").get().await().toObjects(FirebaseTeamMember::class.java)
    }

}