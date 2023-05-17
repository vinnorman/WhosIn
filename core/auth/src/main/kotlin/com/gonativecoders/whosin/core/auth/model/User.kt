package com.gonativecoders.whosin.core.auth.model

data class User(
    val id: String,
    val name: String,
    val currentTeam: UserTeam? = null,
    val email: String,
    val hasSetupProfile: Boolean,
    val photoUri: String? = null
)  {

    data class UserTeam(
        val id: String,
        val name: String
    )

}
