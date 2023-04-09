package com.gonativecoders.whosin.data.team

import com.gonativecoders.whosin.data.team.model.Team
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class TeamRepository(private val service: TeamService) {

    suspend fun createTeam(name: String): Team {
        val firebaseUser = Firebase.auth.currentUser ?: throw Exception("No logged in user!")
        return service.createTeam(firebaseUser.uid, name)
    }

    suspend fun joinTeam(code: String) {
        val firebaseUser = Firebase.auth.currentUser ?: throw Exception("No logged in user!")
        service.joinTeam(firebaseUser.uid, code)
    }

    suspend fun getTeam(teamId: String): Team {
        return service.getTeam(teamId)
    }

}