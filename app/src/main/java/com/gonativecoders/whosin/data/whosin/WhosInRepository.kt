package com.gonativecoders.whosin.data.whosin

import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import kotlinx.coroutines.flow.Flow
import java.util.Date

class WhosInRepository(private val service: WhosInService) {

    suspend fun getWeek(teamId: String, date: Date): Flow<List<WorkDay>> {
        return service.getWeek(teamId, date)
    }

    suspend fun updateAttendance(userId: String, teamId: String, day: WorkDay) {
        val alreadyIn = day.attendance.any { attendee -> attendee.userId == userId}
        service.updateAttendance(teamId, day, Attendee(userId), !alreadyIn)
    }

}