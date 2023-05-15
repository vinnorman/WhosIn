package com.gonativecoders.whosin.core.util.calendar

import java.util.*

val Date.weekString: String
    get() = getWorkingWeekCalendar(this).get(Calendar.WEEK_OF_YEAR).toString()

val Date.yearString: String
    get() = getWorkingWeekCalendar(this).get(Calendar.YEAR).toString()