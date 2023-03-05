package com.gonativecoders.whosin.data.auth.model

import com.google.firebase.firestore.DocumentId

data class User(
    val name: String = "",
    val teams: List<UserTeam>? = null
) {

    @DocumentId
    lateinit var id: String

}

data class UserTeam(
    val teamCode: String? = "",
    val teamId: String? = "",
    val teamName: String? = ""
)
