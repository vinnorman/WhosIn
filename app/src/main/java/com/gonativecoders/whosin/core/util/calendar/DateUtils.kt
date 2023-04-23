package com.gonativecoders.whosin.core.util.calendar

import java.util.*

val Date.weekString: String
    get() = getCalendarFromDate(this).get(Calendar.WEEK_OF_YEAR).toString()

val Date.yearString: String
    get() = getCalendarFromDate(this).get(Calendar.YEAR).toString()