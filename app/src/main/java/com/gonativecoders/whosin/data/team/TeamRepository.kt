package com.gonativecoders.whosin.data.team

import com.gonativecoders.whosin.data.team.model.Team

class TeamRepository(private val service: TeamService) {

    suspend fun createTeam(name: String): Team {
       return service.createTeam(name)
    }
}