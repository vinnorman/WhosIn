package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.whosin.model.Week
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class WhosInService {


    suspend fun getWeek(teamId: String, year: Int, week: Int): Week? {
        val result: DocumentSnapshot = Firebase.firestore
            .collection("teams")
            .document(teamId)
            .collection("years")
            .document(year.toString())
            .collection("weeks")
            .document(week.toString())
            .get().await()

        return null

    }
}