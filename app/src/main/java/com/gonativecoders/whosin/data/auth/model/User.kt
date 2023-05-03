package com.gonativecoders.whosin.data.auth.model

import com.google.firebase.firestore.DocumentId

data class User(
    val name: String = "",
    val initialsColor: String = "",
    val team: UserTeam? = null,
    val email: String = ""
) {

    @DocumentId
    lateinit var id: String

}

data class UserTeam(
    val code: String? = "",
    val id: String? = "",
    val name: String? = ""
)
