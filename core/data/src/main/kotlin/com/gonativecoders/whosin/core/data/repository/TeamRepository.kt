package com.gonativecoders.whosin.core.data.repository

import com.gonativecoders.whosin.core.data.repository.model.Team
import com.gonativecoders.whosin.core.data.repository.model.User
import com.gonativecoders.whosin.core.data.service.TeamService
import com.gonativecoders.whosin.core.data.service.model.toTeam
import com.gonativecoders.whosin.core.data.service.model.toUsers

class TeamRepository internal constructor(private val service: TeamService) {

    suspend fun isTeamNameAvailable(teamId: String): Boolean {
        return service.isTeamIdAvailable(teamId)
    }

    suspend fun createTeam(userId: String, name: String, teamId: String): Team {
        return service.createTeam(userId, name, teamId).toTeam()
    }

    suspend fun joinTeam(userId: String, teamId: String): Team {
        return service.joinTeam(userId, teamId).toTeam()
    }

    suspend fun getTeam(teamId: String): Team {
        return service.getTeam(teamId).toTeam()
    }

    suspend fun getTeamMembers(teamId: String): List<User> {
        return service.getTeamMembers(teamId).toUsers()
    }

    suspend fun editTeam(teamId: String, name: String) {

    }


}