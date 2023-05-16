package com.gonativecoders.whosin.core.data.service.model

import com.gonativecoders.whosin.core.data.repository.model.Team
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class FirebaseTeam(
    val name: String? = null,
    val createdBy: String? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
) {

    @DocumentId
    lateinit var id: String

}

fun FirebaseTeam.toTeam(): Team {
   return  Team(
        id = id,
        name = name!!,
        admins = listOf(createdBy!!),
        createdAt = createdAt!!,
    )
}