package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import java.util.*

class WhosInRepository(private val service: WhosInService) {

    suspend fun getWeek(teamId: String, date: Date): List<WorkDay> {
        return service.getWeek(teamId, date)
    }

    suspend fun getTeam(teamId: String): Team {
        return service.getTeam(teamId)
    }

    suspend fun updateAttendance(userId: String, teamId: String, day: WorkDay) {
        val alreadyIn = day.attendance.any { attendee -> attendee.userId == userId}
        service.updateAttendance(teamId, day, Attendee(userId), !alreadyIn)
    }

}