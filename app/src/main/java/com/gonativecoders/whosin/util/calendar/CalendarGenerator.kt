package com.gonativecoders.whosin.util.calendar

import java.util.*

fun getCurrentWeekCalendar(date: Date): Calendar {
    return GregorianCalendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        time = date
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    }
}