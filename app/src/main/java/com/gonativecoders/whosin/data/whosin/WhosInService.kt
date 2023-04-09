package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.Week
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import com.gonativecoders.whosin.util.calendar.getCalendarFromDate
import com.gonativecoders.whosin.util.calendar.getCurrentWorkingWeek
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

        val calendar = getCurrentWorkingWeek(date)
        val (year, week) = calendar.run { get(Calendar.YEAR) to get(Calendar.WEEK_OF_YEAR) }


        val weekDocument = database.collection("teams")
            .document(teamId)
            .collection("years")
            .document(year.toString())
            .collection("weeks")
            .document(week.toString())

        val result = weekDocument
            .collection("days")
            .get().await()

        return if (!result.isEmpty) {
            result.toObjects()
        } else {
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

    suspend fun getUser(userId: String): User {
        return database.collection("users").document(userId).get().await().toObject() ?: throw Exception("No user found")
    }

    suspend fun updateAttendance(teamId: String, day: WorkDay, attendee: Attendee, isAttending: Boolean) {
        val calendar = getCalendarFromDate(day.date)
        val (year, weekNumber) = calendar.run {
            get(Calendar.YEAR) to get(Calendar.WEEK_OF_YEAR)
        }

        val data = mapOf(
            "attendance" to if (isAttending) FieldValue.arrayUnion(attendee) else FieldValue.arrayRemove(attendee)
        )


        val dayDocument = database.collection("teams")
            .document(teamId)
            .collection("years")
            .document(year.toString())
            .collection("weeks")
            .document(weekNumber.toString())
            .collection("days")
            .document(day.id)

        if (dayDocument.get().await().exists()) {
            dayDocument.update(data).await()
        } else {
            dayDocument.set(data).await()
        }
    }
}