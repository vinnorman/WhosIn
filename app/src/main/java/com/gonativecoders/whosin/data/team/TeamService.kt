package com.gonativecoders.whosin.data.team

interface TeamService {

    suspend fun createTeam(teamName: String, onResult: (Throwable?) -> Unit)

}