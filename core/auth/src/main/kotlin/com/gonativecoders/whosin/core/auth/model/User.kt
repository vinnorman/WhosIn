package com.gonativecoders.whosin.core.auth.model

data class User(
    val id: String,
    val name: String,
    val currentTeamId: String? = null,
    val teams: List<String> = listOf(),
    val email: String,
    val hasSetupProfile: Boolean,
    val photoUri: String? = null
)