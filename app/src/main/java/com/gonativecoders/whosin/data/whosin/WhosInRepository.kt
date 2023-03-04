package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Week
import java.util.*

class WhosInRepository(private val service: WhosInService) {

    suspend fun getWeek(teamId: String, date: Date): Week? {
        val (year, week) = Calendar.getInstance().run {
            time = date
            get(Calendar.YEAR) to get(Calendar.WEEK_OF_YEAR)
        }

        return service.getWeek(teamId, year, week)


    }

    suspend fun getTeam(teamId: String): Team {
        return service.getTeam(teamId)
    }

}