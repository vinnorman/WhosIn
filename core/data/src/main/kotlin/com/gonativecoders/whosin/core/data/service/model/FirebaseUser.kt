package com.gonativecoders.whosin.core.data.service.model

import com.gonativecoders.whosin.core.data.repository.model.User
import com.google.firebase.firestore.DocumentId

data class FirebaseUser(
    val name: String = "",
    val initialsColor: String = "",
    val team: User.UserTeam? = null,
    val email: String = "",
    var hasSetupProfile: Boolean = false,
    var photoUri: String? = null
) {

    @DocumentId
    lateinit var id: String

}

fun FirebaseUser.toUser(): User {
    return  User(
        id = id,
        name = name,
        email = email,
        teams = listOf(team!!),
        hasSetupProfile = hasSetupProfile,
        photoUri = photoUri
    )
}

fun List<FirebaseUser>.toUsers() : List<User> {
    return this.map { it.toUser() }
}
