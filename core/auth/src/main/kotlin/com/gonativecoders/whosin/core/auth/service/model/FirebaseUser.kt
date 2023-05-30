package com.gonativecoders.whosin.core.auth.service.model

import androidx.annotation.Keep
import com.gonativecoders.whosin.core.auth.model.User
import com.google.firebase.firestore.DocumentId

@Keep
data class FirebaseUser(
    val name: String = "",
    val currentTeamId: String? = null,
    val teams: List<String> = listOf(),
    val email: String = "",
    var hasSetupProfile: Boolean = false,
    var photoUri: String? = null
) {

    @DocumentId
    lateinit var id: String

    companion object {

        fun parse(user: User): FirebaseUser {
            return FirebaseUser(
                name = user.name,
                currentTeamId  = user.currentTeamId,
                teams = user.teams,
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
    currentTeamId = currentTeamId,
    teams = teams,
    email = email,
    hasSetupProfile = hasSetupProfile,
    photoUri = photoUri
)

