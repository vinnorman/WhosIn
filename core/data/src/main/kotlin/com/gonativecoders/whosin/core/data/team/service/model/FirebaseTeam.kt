package com.gonativecoders.whosin.core.data.team.service.model

import androidx.annotation.Keep
import com.gonativecoders.whosin.core.data.team.model.Team
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

@Keep
internal data class FirebaseTeam(
    val name: String? = null,
    val createdBy: String? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
) {

    @DocumentId
    lateinit var id: String

}

internal fun FirebaseTeam.toTeam(): Team {
   return  Team(
        id = id,
        name = name!!,
        admins = listOf(createdBy!!),
        createdAt = createdAt!!,
    )
}