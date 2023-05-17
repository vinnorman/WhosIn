package com.gonativecoders.whosin.data.auth.model

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId

@Keep
data class User(
    val name: String = "",
    val initialsColor: String = "",
    val team: UserTeam? = null,
    val email: String = "",
    var hasSetupProfile: Boolean = false,
    var photoUri: String? = null
) {

    @DocumentId
    lateinit var id: String

}

@Keep
data class UserTeam(
    val id: String? = "",
    val name: String? = ""
)
