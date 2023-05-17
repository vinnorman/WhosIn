package com.gonativecoders.whosin.core.data.whosin.model

import java.util.Date

data class WorkDay(
    val id: String,
    val date: Date,
    val attendance: List<Attendee>
)