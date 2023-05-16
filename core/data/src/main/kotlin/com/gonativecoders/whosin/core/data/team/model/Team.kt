package com.gonativecoders.whosin.core.data.team.model

import java.util.Date

data class Team(
    val id: String,
    val admins: List<String>,
    val createdAt: Date,
    val name: String
)
