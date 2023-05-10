package com.gonativecoders.whosin.data.team.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Team(
    val name: String? = null,
    val createdBy: String? = null,
    val code: String? = null,
    @ServerTimestamp val createdAt: Date? = null,
) {

    @DocumentId lateinit var id: String

}