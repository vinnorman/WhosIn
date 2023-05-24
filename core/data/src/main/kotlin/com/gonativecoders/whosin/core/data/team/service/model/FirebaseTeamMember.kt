package com.gonativecoders.whosin.core.data.team.service.model

import androidx.annotation.Keep
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.google.firebase.firestore.DocumentId

@Keep
internal data class FirebaseTeamMember(
    val name: String = "",
    val initialsColor: String = "",
    val email: String = "",
    var hasSetupProfile: Boolean = false,
    var photoUri: String? = null
) {

    @DocumentId
    lateinit var id: String

}

internal fun FirebaseTeamMember.toTeamMember() = TeamMember(
    id = id,
    name = name,
    email = email,
    photoUri = photoUri
)

internal fun List<FirebaseTeamMember>.toTeamMembers() = this.map { it.toTeamMember() }
