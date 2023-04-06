package com.gonativecoders.whosin.data.team

import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.util.getRandomString
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class TeamService(private val database: FirebaseFirestore = Firebase.firestore) {

    suspend fun createTeam(userId: String, teamName: String): Team {
        val user: User = database.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")

        val team = Team(
            name = teamName,
            createdBy = userId,
            code = getRandomString(6)
        )
        val document = database.collection("teams")
            .add(team)
            .await()
        team.id = document.id
        addTeamToUser(userId = user.id, team = team)
        addUserToTeam(user, team.id)
        return team
    }

    suspend fun joinTeam(userId: String, code: String) {
        val user: User = database.collection("users").document(userId).get().await().toObject() ?: throw Exception("User not found")
        val team: Team = database.collection("teams").whereEqualTo("code", code).get().await().first().toObject()
        addTeamToUser(userId, team)
        addUserToTeam(user, team.id)
    }

    private suspend fun addTeamToUser(userId: String, team: Team) {
        database.collection("users").document(userId)
            .update(
                "team", mapOf(
                    "code" to team.code,
                    "id" to team.id,
                    "name" to team.name,
                )
            ).await()
    }

    private suspend fun addUserToTeam(user: User, teamId: String) {
        database.collection("teams").document(teamId)
            .update(
                "members", FieldValue.arrayUnion(
                    mapOf(
                        "displayName" to user.name,
                        "avatar" to 0,
                        "id" to user.id
                    )
                )
            ).await()
    }

}