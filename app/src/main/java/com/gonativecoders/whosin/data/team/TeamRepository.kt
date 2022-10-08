package com.gonativecoders.whosin.data.team

class TeamRepository(private val teamService: TeamService) {

    fun createTeam(teamName: String, onResult: (Throwable?) -> Unit) {
        teamService.createTeam(teamName, onResult)
    }
}