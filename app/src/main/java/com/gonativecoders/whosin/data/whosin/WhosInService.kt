package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.Week
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import com.gonativecoders.whosin.core.util.calendar.getWorkingWeekCalendar
import com.gonativecoders.whosin.core.util.calendar.weekString
import com.gonativecoders.whosin.core.util.calendar.yearString
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.*

class WhosInService(private val database: FirebaseFirestore = Firebase.firestore) {

    suspend fun getWeek(teamId: String, date: Date): List<WorkDay> {
        val weekDocument = database.collection("teams")
            .document(teamId)
            .collection("years")
            .document(date.yearString)
            .collection("weeks")
            .document(date.weekString)

        val result = weekDocument
            .collection("days")
            .get().await()

        return if (!result.isEmpty) {
            result.toObjects()
        } else {
            val calendar = getWorkingWeekCalendar(date)
            weekDocument.set(Week(calendar.time)).await()

            val workDays = listOf(
                WorkDay(calendar.time),
                WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
                WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
                WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
                WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            )

            workDays.forEach { day ->
                val dayOfWeek = Calendar.getInstance().apply { time = day.date }.get(Calendar.DAY_OF_WEEK)
                weekDocument.collection("days").document(dayOfWeek.toString()).set(day)
                day.id = dayOfWeek.toString()
            }

            workDays
        }
    }

    suspend fun getTeam(teamId: String): Team {
        return database.collection("teams").document(teamId).get().await().toObject<Team>() ?: throw Exception("Team not found")
    }

    suspend fun updateAttendance(teamId: String, day: WorkDay, attendee: Attendee, isAttending: Boolean) {
        val data = mapOf(
            "attendance" to if (isAttending) FieldValue.arrayUnion(attendee) else FieldValue.arrayRemove(attendee)
        )

        val dayDocument = database.collection("teams")
            .document(teamId)
            .collection("years")
            .document(day.date.yearString)
            .collection("weeks")
            .document(day.date.weekString)
            .collection("days")
            .document(day.id)

        if (dayDocument.get().await().exists()) {
            dayDocument.update(data).await()
        } else {
            dayDocument.set(data).await()
        }
    }
}