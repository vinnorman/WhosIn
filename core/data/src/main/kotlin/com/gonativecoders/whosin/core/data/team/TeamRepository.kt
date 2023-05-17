package com.gonativecoders.whosin.core.data.team

import com.gonativecoders.whosin.core.data.team.model.Team
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.gonativecoders.whosin.core.data.team.service.TeamService

class TeamRepository internal constructor(private val service: TeamService) {

    suspend fun isTeamNameAvailable(teamId: String): Boolean {
        return service.isTeamIdAvailable(teamId)
    }

    suspend fun createTeam(userId: String, name: String, teamId: String): Team {
        return service.createTeam(userId, name, teamId)
    }

    suspend fun joinTeam(userId: String, teamId: String): Team {
        return service.joinTeam(userId, teamId)
    }

    suspend fun getTeam(teamId: String): Team {
        return service.getTeam(teamId)
    }

    suspend fun getTeamMembers(teamId: String): List<TeamMember> {
        return service.getTeamMembers(teamId)
    }

    suspend fun editTeam(teamId: String, name: String) {

    }


}