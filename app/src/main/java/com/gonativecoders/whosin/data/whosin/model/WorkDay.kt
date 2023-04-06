package com.gonativecoders.whosin.data.whosin.model

import com.google.firebase.firestore.DocumentId
import java.util.*

data class WorkDay(
    val date: Date = Date(),
    val attendance: List<Attendee> = listOf()
) {

    @DocumentId
    lateinit var id: String

}