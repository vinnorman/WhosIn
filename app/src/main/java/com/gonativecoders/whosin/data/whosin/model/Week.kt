package com.gonativecoders.whosin.data.whosin.model

import java.util.*

data class Week(
    val startDate: Date,
    val days: List<WorkDay>
)

data class WorkDay(
    val day: String,
    val number: Int,
    val attendees: List<User>
)

data class User(
    val id: Int,
    val nickName: String,
    val avatar: Int = 0
)
