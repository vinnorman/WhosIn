package com.gonativecoders.whosin.core.data.whosin.service

import com.gonativecoders.whosin.core.data.calendar.getWorkingWeekCalendar
import com.gonativecoders.whosin.core.data.whosin.model.WorkDay
import com.gonativecoders.whosin.core.data.whosin.service.model.FirebaseAttendee
import com.gonativecoders.whosin.core.data.whosin.service.model.FirebaseWorkDay
import com.gonativecoders.whosin.core.data.whosin.service.model.toWorkDay
import com.google.firebase.firestore.DocumentReference
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

internal class WhosInService(private val firestore: FirebaseFirestore = Firebase.firestore) {

    fun getWeek(teamId: String, date: Date): Flow<List<WorkDay>> {
        val calendar = getWorkingWeekCalendar(date)
        val weekDocument: DocumentReference = firestore.collection("teams")
            .document(teamId)
            .collection("years")
            .document(calendar.year)
            .collection("weeks")
            .document(calendar.weekOfYear)

        return weekDocument
            .collection("days")
            .snapshots()
            .map {
                if (it.isEmpty) addWeek(weekDocument, calendar)
                if (it.size() < 5) listOf()
                else it.toObjects<FirebaseWorkDay>().map { firebaseWorkDay -> firebaseWorkDay.toWorkDay() }
            }
    }

    private suspend fun addWeek(weekDocument: DocumentReference, calendar: Calendar) {
        val workDays = listOf(
            FirebaseWorkDay(calendar.time),
            FirebaseWorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            FirebaseWorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            FirebaseWorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            FirebaseWorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
        )

        firestore.runBatch {
            weekDocument.set(mapOf("startDate" to calendar.time))
            workDays.forEach { day ->
                val dayOfWeek = Calendar.getInstance().apply { time = day.date }.get(Calendar.DAY_OF_WEEK)
                weekDocument.collection("days").document(dayOfWeek.toString()).set(day)
            }
        }.await()
    }

    suspend fun updateAttendance(teamId: String, day: WorkDay, userId: String, isAttending: Boolean) {
        val calendar = getWorkingWeekCalendar(day.date)
        val data = mapOf(
            "attendance" to if (isAttending) FieldValue.arrayUnion(FirebaseAttendee(userId)) else FieldValue.arrayRemove(FirebaseAttendee(userId))
        )

        val dayDocument = firestore.collection("teams")
            .document(teamId)
            .collection("years")
            .document(calendar.year)
            .collection("weeks")
            .document(calendar.weekOfYear)
            .collection("days")
            .document(day.id)

        if (dayDocument.get().await().exists()) dayDocument.update(data).await() else dayDocument.set(data).await()
    }
}

private val Calendar.weekOfYear: String
    get() = get(Calendar.WEEK_OF_YEAR).toString()

private val Calendar.year: String
    get() = get(Calendar.YEAR).toString()