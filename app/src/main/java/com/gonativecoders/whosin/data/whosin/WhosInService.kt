package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.core.util.calendar.getWorkingWeekCalendar
import com.gonativecoders.whosin.core.util.calendar.weekString
import com.gonativecoders.whosin.core.util.calendar.yearString
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.Week
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date

class WhosInService(private val database: FirebaseFirestore = Firebase.firestore) {

    suspend fun getWeek(teamId: String, date: Date): Flow<List<WorkDay>> {
        return database.collection("teams")
            .document(teamId)
            .collection("years")
            .document(date.yearString)
            .collection("weeks")
            .document(date.weekString)
            .collection("days")
            .snapshots()
            .map {
                if (it.isEmpty) {
                    addWeek(teamId, date)
                }
                it.toObjects()
            }
    }

    private suspend fun addWeek(teamId: String, date: Date) {
        val calendar = getWorkingWeekCalendar(date)
        val weekDocument = database.collection("teams")
            .document(teamId)
            .collection("years")
            .document(date.yearString)
            .collection("weeks")
            .document(date.weekString)

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