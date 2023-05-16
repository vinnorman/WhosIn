package com.gonativecoders.whosin.core.data.repository.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    // Todo: this should just be the regular Team Class
    val teams: List<UserTeam>,
    var hasSetupProfile: Boolean,
    var photoUri: String?
) {

    // Todo: this should just be the regular Team class
    data class UserTeam(
        val id: String? = "",
        val name: String? = ""
    )
}


