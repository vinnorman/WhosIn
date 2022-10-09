package com.gonativecoders.whosin.data.team

interface TeamService {

    fun createTeam(teamName: String, onResult: (Throwable?) -> Unit)

}