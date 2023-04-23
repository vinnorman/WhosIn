package com.gonativecoders.whosin.core.util.calendar

import java.util.*



fun getWorkingWeekCalendar(date: Date): Calendar {
    return GregorianCalendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        minimalDaysInFirstWeek = 4
        time = date
        // If saturday or Sunday, move to the week ahead
        if (get(Calendar.DAY_OF_WEEK) == 7 || get(Calendar.DAY_OF_WEEK) == 1) {
            add(Calendar.WEEK_OF_YEAR, 1)
        }
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
    }
}

fun getCalendarFromDate(date: Date): Calendar {
    return GregorianCalendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        minimalDaysInFirstWeek = 4
        time = date
    }
}