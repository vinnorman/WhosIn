@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.auth.model.UserTeam
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import com.gonativecoders.whosin.ui.composables.InitialsCircle
import com.gonativecoders.whosin.ui.theme.*
import com.gonativecoders.whosin.util.calendar.dayOfMonth
import com.gonativecoders.whosin.util.calendar.dayOfWeek
import java.util.*

@Composable
fun WhosInScreen(viewModel: WhosInViewModel) {
    when (val uiState = viewModel.uiState) {
        is WhosInViewModel.UiState.Error -> {}
        WhosInViewModel.UiState.Loading -> Loading()
        is WhosInViewModel.UiState.Success -> WhosInContent(
            days = uiState.workDays,
            userId = uiState.user.id,
            onDayClicked = { day -> viewModel.updateAttendance(day) },
            team = uiState.team
        )
    }
}

@Composable
fun WhosInContent(
    days: List<WorkDay>,
    userId: String,
    team: Team,
    onDayClicked: (WorkDay) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Current Week")
        WeekView(days = days, userId = userId, team = team, onDayClicked = onDayClicked)
    }
}

@Composable
fun Loading() {
    CircularProgressIndicator()
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

        DayHeader(userId = userId, day = day, onCardClicked = onDayClicked)
        Spacer(modifier = Modifier.height(24.dp))
        AvatarList(team, day.attendance)
    }
}

@Composable
fun DayHeader(
    userId: String,
    day: WorkDay,
    modifier: Modifier = Modifier,
    onCardClicked: (WorkDay) -> Unit
) {
    var showCheckMark: Boolean by remember { mutableStateOf(day.attendance.any { it.userId == userId }) }

    Box {
        Card(modifier = modifier,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            onClick = {
                onCardClicked(day)
                showCheckMark = !showCheckMark
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
        if (showCheckMark) {
            Image(
                painter = painterResource(R.drawable.checked),
                contentDescription = "Check",
                modifier = Modifier
                    .padding(end = 2.dp)
                    .size(12.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
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
        Text(text = member.displayName, textAlign = TextAlign.Center)
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

        val user = User(name = "Vin", initialsColor = "FF0000", team = UserTeam("", "1", "My team")).apply { id = "1" }
        val team = Team("Some team", members = listOf(Member("1")))

        WhosInTheme {
            WhosInContent(
                days = workDays,
                userId = user.id,
                onDayClicked = { },
                team = team
            )
        }

    }
}