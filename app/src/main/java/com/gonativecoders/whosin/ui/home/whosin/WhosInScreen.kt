@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.whosin

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.OutlinedIconButton
import com.gonativecoders.whosin.core.components.UserPhoto
import com.gonativecoders.whosin.core.theme.Blue50
import com.gonativecoders.whosin.core.theme.Grey50
import com.gonativecoders.whosin.core.theme.Grey600
import com.gonativecoders.whosin.core.theme.Grey800
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.core.util.calendar.dayOfMonth
import com.gonativecoders.whosin.core.util.calendar.dayOfWeek
import com.gonativecoders.whosin.core.util.calendar.getCalendarFromDate
import com.gonativecoders.whosin.core.util.calendar.getWorkingWeekCalendar
import com.gonativecoders.whosin.core.util.calendar.shortDate
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.auth.model.UserTeam
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import java.util.Calendar
import java.util.Date

val today: Date = Calendar.getInstance().time

@Composable
fun WhosInScreen(
    viewModel: WhosInViewModel
) {
    when (val uiState = viewModel.uiState) {
        is WhosInViewModel.UiState.Error -> {
            Log.e("vin", uiState.error.message!!, uiState.error)
        }
        WhosInViewModel.UiState.Loading -> Loading()
        is WhosInViewModel.UiState.Success -> WhosInContent(
            days = uiState.workDays,
            userId = uiState.user.id,
            onDayClicked = { day -> viewModel.updateAttendance(day) },
            members = uiState.members,
            onNextWeekClicked = { viewModel.goToNextWeek() },
            onPreviousWeekClicked = { viewModel.goToPreviousWeek() },
            onTodayClicked = { viewModel.goToToday() },
            onDateSelected = { date -> viewModel.goToDate(date)}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhosInContent(
    days: List<WorkDay>,
    userId: String,
    members: List<User>,
    onDayClicked: (WorkDay) -> Unit,
    onNextWeekClicked: () -> Unit,
    onPreviousWeekClicked: () -> Unit,
    onTodayClicked: () -> Unit,
    onDateSelected: (Date) -> Unit,
) {

    var showCalendar by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Calendar.getInstance().timeInMillis)

    if (showCalendar) {
        DatePickerDialog(
            onDismissRequest = { showCalendar = false },
            confirmButton = {
                TextButton(onClick = {
                    showCalendar = false
                    onDateSelected(Date(datePickerState.selectedDateMillis ?: return@TextButton))
                }) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCalendar = false }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }


    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 12.dp)
    ) {
        WeekNavigation(
            days = days,
            onTodayClicked = onTodayClicked,
            onPreviousWeekClicked = onPreviousWeekClicked,
            onNextWeekClicked = onNextWeekClicked,
            onCalendarClicked = { showCalendar = true }
        )
        WeekView(
            days = days,
            userId = userId,
            members = members,
            onDayClicked = onDayClicked
        )

    }
}

@ExperimentalMaterial3Api
@Composable
private fun WeekNavigation(
    days: List<WorkDay>,
    onTodayClicked: () -> Unit,
    onPreviousWeekClicked: () -> Unit,
    onNextWeekClicked: () -> Unit,
    onCalendarClicked: () -> Unit,
) {
    val currentWeek = getWorkingWeekCalendar(today)
    val firstDay = getCalendarFromDate(days.first().date)
    val lastDay = getCalendarFromDate(days.last().date)

    val dateText = if (currentWeek.get(Calendar.WEEK_OF_YEAR) == firstDay.get(Calendar.WEEK_OF_YEAR)) {
        "Current Week"
    } else if (firstDay.get(Calendar.MONTH) == lastDay.get(Calendar.MONTH)) {
        "${dayOfMonth.format(firstDay.time)} - ${shortDate.format(lastDay.time)}"
    } else {
        "${shortDate.format(firstDay.time)} - ${shortDate.format(lastDay.time)}"
    }

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedIconButton(
                onClick = onPreviousWeekClicked,
                icon = Icons.Rounded.ArrowBack,
                contentDescription = "Previous Week"
            )
            Text(
                text = dateText,
                modifier = Modifier
                    .padding(12.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            OutlinedIconButton(
                onClick = onNextWeekClicked,
                icon = Icons.Rounded.ArrowForward,
                contentDescription = "Next Week"
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            OutlinedIconButton(
                onClick = onCalendarClicked,
                icon = Icons.Rounded.CalendarMonth,
                contentDescription = "Next Week"
            )

        }
    }

}


@Composable
fun WeekView(
    days: List<WorkDay>,
    userId: String,
    members: List<User>,
    onDayClicked: (WorkDay) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
    ) {

        days.forEach { day ->
            Spacer(modifier = Modifier.width(4.dp))
            DayColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                userId = userId,
                day = day,
                members = members,
                onDayClicked = onDayClicked
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

    }
}

@Composable
fun DayColumn(
    modifier: Modifier,
    userId: String,
    day: WorkDay,
    onDayClicked: (WorkDay) -> Unit,
    members: List<User>
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DayHeader(
            day = day,
            onCardClicked = onDayClicked,
            isAttending = day.attendance.any { it.userId == userId }
        )
        AvatarList(members, day.attendance)
    }
}

@Composable
fun DayHeader(
    day: WorkDay,
    modifier: Modifier = Modifier,
    onCardClicked: (WorkDay) -> Unit,
    isAttending: Boolean
) {
    val cardColor = if (getCalendarFromDate(day.date).get(Calendar.DAY_OF_YEAR) == getCalendarFromDate(today).get(Calendar.DAY_OF_YEAR)) Blue50 else Grey50
    Box {
        Card(modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = cardColor),
            onClick = {
                onCardClicked(day)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                Text(
                    text = day.date.dayOfWeek().uppercase(),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey600
                )
                Text(
                    text = day.date.dayOfMonth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Grey800
                )
            }
        }
        if (isAttending) {
            Image(
                imageVector = Icons.Filled.Check,
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "Check",
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .size(16.dp)
                    .padding(2.dp)
                    .align(Alignment.BottomEnd)

            )
        }

    }

}

@Composable
private fun AvatarList(members: List<User>, attendees: List<Attendee>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val teamMembers = attendees.mapNotNull { attendee ->
            members.find { it.id == attendee.userId }
        }
        teamMembers.forEach { teamMember ->
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Avatar(teamMember)
            }
        }
    }
}

@Composable
fun Avatar(user: User) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        UserPhoto(user = user)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = user.name.split(" ").first(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            lineHeight = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WhosInScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val calendar = Calendar.getInstance()

        val workDays = listOf(
            WorkDay(calendar.time, listOf(Attendee("1"), Attendee("2"))),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
        )

        val user = User(name = "Vin Norman", initialsColor = "18434129578667540480", team = UserTeam("", "1", "My team"), email = "vin.norman@gmail.com").apply { id = "1" }

        WhosInTheme {
            WhosInContent(
                days = workDays,
                userId = user.id,
                onDayClicked = { },
                members = listOf(),
                onNextWeekClicked = {},
                onPreviousWeekClicked = {},
                onTodayClicked = {},
                onDateSelected = {}
            )
        }

    }
}