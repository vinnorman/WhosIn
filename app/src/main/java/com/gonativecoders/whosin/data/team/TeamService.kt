package com.gonativecoders.whosin.data.team

import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Team
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class TeamService(private val database: FirebaseFirestore = Firebase.firestore) {

    suspend fun isTeamIdAvailable(userId: String, teamId: String): Boolean {
        return !database.collection("teams").document(teamId).get().await().exists()
    }

    suspend fun createTeam(userId: String, teamName: String, teamId: String): Team {
        val user: User = database.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team = Team(
            name = teamName,
            createdBy = userId
        )
        database.collection("teams").document(teamId).set(team) .await()
        team.id = teamId
        addTeamToUser(userId = user.id, team = team)
        addUserToTeam(user, team.id)
        return team
    }

    suspend fun joinTeam(userId: String, teamId: String): Team {
        val user: User = database.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team: Team = database.collection("teams").document(teamId).get().await().toObject() ?: throw Exception("Team not found")
        addTeamToUser(userId, team)
        addUserToTeam(user, team.id)
        return team
    }

    private suspend fun addTeamToUser(userId: String, team: Team) {
        database.collection("users").document(userId)
            .update(
                "team", mapOf(
                    "id" to team.id,
                    "name" to team.name,
                )
            ).await()
    }

    private suspend fun addUserToTeam(user: User, teamId: String) {
        database
            .collection("teams")
            .document(teamId)
            .collection("members")
            .document(user.id).set(user)
            .await()
    }

    suspend fun getTeam(teamId: String): Team {
        return database.collection("teams").document(teamId).get().await().toObject() ?: throw Exception("Team not found")
    }

    suspend fun getTeamMembers(teamId: String): List<User> {
        return database.collection("teams").document(teamId).collection("members").get().await().toObjects(User::class.java)
    }

}