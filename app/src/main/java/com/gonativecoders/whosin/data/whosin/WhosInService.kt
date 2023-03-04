package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.whosin.model.Week
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class WhosInService(val database: FirebaseFirestore = Firebase.firestore) {


    suspend fun getWeek(teamId: String, year: Int, week: Int): Week? {
        val result: DocumentSnapshot = database
            .collection("teams")
            .document(teamId)
            .collection("years")
            .document(year.toString())
            .collection("weeks")
            .document(week.toString())
            .get().await()

        if (!result.exists()) {
            database
                .collection("teams")
                .document(teamId)
                .collection("years")
                .document(year.toString())
                .collection("weeks")
                .document(week.toString())
                .set(
                    mapOf(
                        "startDate" to "04/05/2023",
                        "workDays" to listOf("Mon", "Tue", "Wed  ")
                    )
                )
        }

        return null

    }
}