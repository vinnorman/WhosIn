@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.whosin

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.window.DialogProperties
import com.gonativecoders.whosin.core.components.InitialsCircle
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.OutlinedIconButton
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
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import java.util.Calendar
import java.util.Date

val today: Date = Calendar.getInstance().time

@Composable
fun WhosInScreen(
    viewModel: WhosInViewModel,
    navigate: (String) -> Unit
) {
    when (val uiState = viewModel.uiState) {
        is WhosInViewModel.UiState.Error -> {}
        WhosInViewModel.UiState.Loading -> Loading()
        is WhosInViewModel.UiState.Success -> WhosInContent(
            days = uiState.workDays,
            userId = uiState.user.id,
            onDayClicked = { day -> viewModel.updateAttendance(day) },
            team = uiState.team,
            onNextWeekClicked = { viewModel.goToNextWeek() },
            onPreviousWeekClicked = { viewModel.goToPreviousWeek() },
            onTodayClicked = { viewModel.goToToday() },
            navigate = navigate
        )
    }
}

@Composable
fun WhosInContent(
    days: List<WorkDay>,
    userId: String,
    team: Team,
    onDayClicked: (WorkDay) -> Unit,
    onNextWeekClicked: () -> Unit,
    onPreviousWeekClicked: () -> Unit,
    onTodayClicked: () -> Unit,
    navigate: (String) -> Unit
) {

    var showCalendar by remember { mutableStateOf(false) }

    if (showCalendar) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { showCalendar = false },


        ) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)

                androidx.compose.material3.DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }


        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp)
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
            team = team,
            onDayClicked = onDayClicked
        )

    }
}

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


//        if (currentWeek.get(Calendar.WEEK_OF_YEAR) == firstDay.get(Calendar.WEEK_OF_YEAR)) {
//            Text(
//                text = "Current Week", modifier = Modifier
//                    .weight(1.0f)
//                    .padding(start = 12.dp), textAlign = TextAlign.Start
//            )
//        } else {
//            IconButton(onClick = onTodayClicked) {
//                Icon(
//                    imageVector = Icons.Rounded.Today,
//                    contentDescription = "Today",
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//            }
//            var dateRange = monthFormatter.format(firstDay.time)
//            if (firstDay.get(Calendar.MONTH) != lastDay.get(Calendar.MONTH)) {
//                dateRange += " - ${monthFormatter.format(lastDay.time)}"
//            }
//            Text(
//                text = dateRange, modifier = Modifier
//                    .weight(1.0f)
//                    .padding(start = 12.dp), textAlign = TextAlign.Start
//            )
//        }

        }
    }

}


@Composable
fun WeekView(
    days: List<WorkDay>,
    userId: String,
    team: Team,
    onDayClicked: (WorkDay) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 12.dp, end = 12.dp)
    ) {

        days.forEach { day ->
            Spacer(modifier = Modifier.width(4.dp))
            DayColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                userId = userId,
                day = day,
                team = team,
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
    team: Team,
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
        Spacer(modifier = Modifier.height(12.dp))
        AvatarList(team, day.attendance)
    }
}

@Composable
fun DayHeader(
    day: WorkDay,
    modifier: Modifier = Modifier,
    onCardClicked: (WorkDay) -> Unit,
    isAttending: Boolean
) {

    Box {
        Card(modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = {
                onCardClicked(day)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text(
                    text = day.date.dayOfWeek(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Grey600
                )
                Text(
                    text = day.date.dayOfMonth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
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
private fun AvatarList(team: Team, attendees: List<Attendee>) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val teamMembers = attendees.mapNotNull { attendee ->
            team.members.find { it.id == attendee.userId }
        }
        teamMembers.forEach { teamMember ->
            item {
                Avatar(teamMember)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun Avatar(member: Member) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InitialsCircle(name = member.displayName, color = member.initialsColor)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = member.displayName, textAlign = TextAlign.Center, fontSize = 14.sp, lineHeight = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun WhosInScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.WEEK_OF_YEAR, 6)
        }

        val workDays = listOf(
            WorkDay(calendar.time, listOf(Attendee("1"))),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
            WorkDay(calendar.apply { add(Calendar.DAY_OF_WEEK, 1) }.time),
        )

        val user = User(name = "Vin Norman", initialsColor = "18434129578667540480", team = UserTeam("", "1", "My team")).apply { id = "1" }
        val team = Team("Some team", members = listOf(Member("1", displayName = "Vin Norman", initialsColor = "18434129578667540480")))

        WhosInTheme {
            WhosInContent(
                days = workDays,
                userId = user.id,
                onDayClicked = { },
                team = team,
                onNextWeekClicked = {},
                onPreviousWeekClicked = {},
                onTodayClicked = {},
                navigate = {}
            )
        }

    }
}