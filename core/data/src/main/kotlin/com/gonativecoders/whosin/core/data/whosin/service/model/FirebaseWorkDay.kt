package com.gonativecoders.whosin.core.data.whosin.service.model

import androidx.annotation.Keep
import com.gonativecoders.whosin.core.data.whosin.model.Attendee
import com.gonativecoders.whosin.core.data.whosin.model.WorkDay
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Keep
data class FirebaseWorkDay(
    val date: Date = Date(),
    val attendance: List<FirebaseAttendee> = listOf()
) {

    @DocumentId
    lateinit var id: String

}

fun FirebaseWorkDay.toWorkDay(): WorkDay = WorkDay(id, date, attendance.map { Attendee(it.userId) })