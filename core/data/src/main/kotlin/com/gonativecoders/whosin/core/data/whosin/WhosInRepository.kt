package com.gonativecoders.whosin.core.data.whosin

import com.gonativecoders.whosin.core.data.whosin.model.WorkDay
import com.gonativecoders.whosin.core.data.whosin.service.WhosInService
import kotlinx.coroutines.flow.Flow
import java.util.Date

class WhosInRepository internal constructor(private val service: WhosInService) {

    fun getWeek(teamId: String, date: Date): Flow<List<WorkDay>> {
        return service.getWeek(teamId, date)
    }

    suspend fun updateAttendance(userId: String, teamId: String, day: WorkDay) {
        val alreadyIn = day.attendance.any { attendee -> attendee.userId == userId }
        service.updateAttendance(teamId, day, userId, !alreadyIn)
    }

}