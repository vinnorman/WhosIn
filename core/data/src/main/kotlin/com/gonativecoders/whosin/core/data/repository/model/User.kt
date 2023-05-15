package com.gonativecoders.whosin.core.data.repository.model

data class User(
    val name: String,
    val email: String,
    val teams: List<Team>,
    var hasSetupProfile: Boolean,
    var photoUri: String?
)
