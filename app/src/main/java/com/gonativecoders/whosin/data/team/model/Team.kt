package com.gonativecoders.whosin.data.team.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FieldValue

data class Team(
    val name: String,
    val createdBy: String,
    val code: String,
    val createdAt: FieldValue = FieldValue.serverTimestamp()
) {

    @DocumentId lateinit var id: String
}
