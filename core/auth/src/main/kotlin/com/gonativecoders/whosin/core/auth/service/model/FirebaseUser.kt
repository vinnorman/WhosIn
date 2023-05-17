package com.gonativecoders.whosin.core.auth.service.model

import androidx.annotation.Keep
import com.gonativecoders.whosin.core.auth.model.User
import com.google.firebase.firestore.DocumentId

@Keep
internal data class FirebaseUser(
    val name: String = "",
    val team: FirebaseUserTeam? = null,
    val email: String = "",
    var hasSetupProfile: Boolean = false,
    var photoUri: String? = null
) {

    @DocumentId
    lateinit var id: String

    @Keep
    data class FirebaseUserTeam(
        val id: String = "",
        val name: String = ""
    )

    companion object {

        fun parse(user: User): FirebaseUser {
            return FirebaseUser(
                name = user.name,
                team = user.currentTeam?.let { FirebaseUserTeam(id = it.id, name = it.name) },
                email = user.email,
                hasSetupProfile = user.hasSetupProfile,
                photoUri = user.photoUri
            ).apply { id = user.id }
        }
    }

}

internal fun FirebaseUser.toUser() = User(
    id = id,
    name = name,
    currentTeam = team?.let { User.UserTeam(id = team.id, name = team.name) },
    email = email,
    hasSetupProfile = hasSetupProfile,
    photoUri = photoUri
)

