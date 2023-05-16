package com.gonativecoders.whosin.core.data.team.model

data class TeamMember(
    val id: String,
    val name: String,
    val email: String,
    var photoUri: String?
)