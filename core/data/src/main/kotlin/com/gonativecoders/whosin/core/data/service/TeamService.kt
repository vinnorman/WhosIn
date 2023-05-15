package com.gonativecoders.whosin.core.data.service

import com.gonativecoders.whosin.core.data.repository.model.Team
import com.gonativecoders.whosin.core.data.repository.model.User

internal class TeamService {

    suspend fun isTeamIdAvailable(teamId: String): Boolean {
        return false
    }

    fun createTeam(userId: String, name: String, teamId: String): Team {
        TODO("Not yet implemented")
    }

    fun joinTeam(userId: String, teamId: String): Team {
        TODO("Not yet implemented")
    }

    fun getTeam(teamId: String): Team {
        TODO("Not yet implemented")
    }

    fun getTeamMembers(teamId: String): List<User> {
        TODO("Not yet implemented")
    }

}