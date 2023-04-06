package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import java.util.*

class WhosInRepository(private val service: WhosInService) {

    suspend fun getUser(userId: String): User {
        return service.getUser(userId)
    }

    suspend fun getWeek(teamId: String, date: Date): List<WorkDay> {
        val (year, week) = Calendar.getInstance().run {
            firstDayOfWeek = Calendar.MONDAY
            time = date
            get(Calendar.YEAR) to get(Calendar.WEEK_OF_YEAR)
        }

        return service.getWeek(teamId, year, week)
    }

    suspend fun getTeam(teamId: String): Team {
        return service.getTeam(teamId)
    }

    suspend fun updateAttendance(userId: String, teamId: String, day: WorkDay) {
        val alreadyIn = day.attendance.any { attendee -> attendee.userId == userId}
        service.updateAttendance(teamId, day, Attendee(userId), !alreadyIn)
    }

}