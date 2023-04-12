package com.gonativecoders.whosin.util.calendar

import java.text.SimpleDateFormat
import java.util.*

val formatter = SimpleDateFormat("EEE\nd MMM", Locale.ENGLISH)
val dayOfWeekFormatter = SimpleDateFormat("EEE", Locale.ENGLISH)
val dayOfMonth = SimpleDateFormat("d", Locale.ENGLISH)

fun Date.dayOfWeek(): String {
    return dayOfWeekFormatter.format(this)
}

fun Date.dayOfMonth(): String {
    return dayOfMonth.format(this)
}