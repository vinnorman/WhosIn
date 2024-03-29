package com.gonativecoders.whosin.core.data.team.service

import com.gonativecoders.whosin.core.data.team.model.Team
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.gonativecoders.whosin.core.data.team.service.model.FirebaseTeam
import com.gonativecoders.whosin.core.data.team.service.model.FirebaseTeamMember
import com.gonativecoders.whosin.core.data.team.service.model.toTeam
import com.gonativecoders.whosin.core.data.team.service.model.toTeamMembers
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

internal class TeamService(private val firestore: FirebaseFirestore = Firebase.firestore) {

    suspend fun isTeamIdAvailable(teamId: String): Boolean {
        return !firestore.collection("teams").document(teamId).get().await().exists()
    }

    suspend fun createTeam(userId: String, name: String, teamId: String): Team {
        val user: FirebaseTeamMember = firestore.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team = FirebaseTeam(
            name = name,
            createdBy = userId
        )
        firestore.collection("teams").document(teamId).set(team).await()
        team.id = teamId
        addTeamToUser(userId = user.id, team = team)
        addUserToTeam(user, team.id)
        return team.toTeam()
    }

    private suspend fun addTeamToUser(userId: String, team: FirebaseTeam) {
        firestore.collection("users").document(userId)
            .update(
                "teams", FieldValue.arrayUnion(team.id),
                "currentTeamId", team.id
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

    suspend fun joinTeam(userId: String, teamId: String): Team {
        val user: FirebaseTeamMember = firestore.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team: FirebaseTeam = firestore.collection("teams").document(teamId).get().await().toObject() ?: throw Exception("Team not found")
        addTeamToUser(userId, team)
        addUserToTeam(user, team.id)
        return team.toTeam()
    }

    suspend fun getTeam(teamId: String): Team {
        return firestore.collection("teams").document(teamId).get().await().toObject<FirebaseTeam>()?.toTeam()
            ?: throw Exception("Team not found")
    }

    fun getTeamFlow(teamId: String): Flow<Team> {
        return firestore.collection("teams").document(teamId).snapshots().map { it.toObject<FirebaseTeam>()?.toTeam() ?: throw Exception("Team not found") }
    }

    suspend fun getTeamMembers(teamId: String): List<TeamMember> {
        return firestore.collection("teams").document(teamId).collection("members").get().await().toObjects(FirebaseTeamMember::class.java)
            .toTeamMembers()
    }

    suspend fun updateTeam(teamId: String, name: String) {
        firestore.collection("teams")
            .document(teamId)
            .update("name", name).await()
    }

    suspend fun leaveTeam(userId: String, teamId: String) {
        firestore.runTransaction {
            firestore.collection("teams")
                .document(teamId)
                .collection("members")
                .document(userId)
                .delete()

            firestore.collection("users")
                .document(userId)
                .update(
                    "currentTeamId", null,
                    "teams", FieldValue.arrayRemove(userId)
                )
        }.await()
    }

}