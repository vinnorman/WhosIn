package com.gonativecoders.whosin.core.util.calendar

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val formatter = SimpleDateFormat("EEE\nd MMM", Locale.ENGLISH)
val dayOfWeekFormatter = SimpleDateFormat("EEE", Locale.ENGLISH)
val dayOfMonth = SimpleDateFormat("d", Locale.ENGLISH)
val monthFormatter = SimpleDateFormat("MMMM", Locale.ENGLISH)
val shortDate = SimpleDateFormat("d MMM", Locale.ENGLISH)

fun Date.dayOfWeek(): String {
    return dayOfWeekFormatter.format(this)
}

fun Date.dayOfMonth(): String {
    return dayOfMonth.format(this)
}