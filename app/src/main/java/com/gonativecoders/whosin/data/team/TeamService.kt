package com.gonativecoders.whosin.data.team

import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.util.getRandomString
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class TeamService {

    suspend fun createTeam(teamName: String): Team {

        val user = Firebase.auth.currentUser ?: throw Exception("No logged in user!")
        val team = Team(
            name = teamName,
            createdBy = user.uid,
            code = getRandomString(6)
        )
        val document = Firebase.firestore.collection("teams")
            .add(team)
            .await()
        team.id = document.id
        addTeamToUser(userId = user.uid, team = team)
        addUserToTeam(user, team.id)
        return team
    }

    private suspend fun addTeamToUser(userId: String, team: Team) {
        Firebase.firestore.collection("users").document(userId)
            .update(
                "teams", FieldValue.arrayUnion(
                    mapOf("teamName" to team.name, "teamCode" to team.code, "teamId" to team.id)
                )
            ).await()
    }

    private suspend fun addUserToTeam(user: FirebaseUser, teamId: String) {
        Firebase.firestore.collection("teams").document(teamId)
            .update(
                "members", FieldValue.arrayUnion(
                    mapOf("name" to user.displayName, "id" to user.uid)
                )
            ).await()
    }

}