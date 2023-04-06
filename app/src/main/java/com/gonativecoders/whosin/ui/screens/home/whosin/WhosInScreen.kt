@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WhosInScreen(viewModel: WhosInViewModel) {
    WhosInContent(
        uiState = viewModel.uiState,
        onDayClicked = { day -> viewModel.updateAttendance(day) }
    )
}

@Composable
fun WhosInContent(
    uiState: WhosInViewModel.UiState,
    onDayClicked: (WorkDay) -> Unit
) {
    when (uiState) {
        is WhosInViewModel.UiState.Error -> {}
        WhosInViewModel.UiState.Loading -> Loading()
        is WhosInViewModel.UiState.Success -> WeekView(
            days = uiState.workDays,
            userId = uiState.user.id,
            onDayClicked = onDayClicked,
            team = uiState.team
        )
    }
}

@Composable
fun Loading() {
    CircularProgressIndicator()
}

@Composable
fun WeekView(days: List<WorkDay>, userId: String, team: Team, onDayClicked: (WorkDay) -> Unit) {
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
        horizontalAlignment = CenterHorizontally
    ) {

        DayHeader(userId = userId, day = day, onCardClicked = onDayClicked)
        Spacer(modifier = Modifier.height(24.dp))
        AvatarList(team, day.attendance)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayHeader(userId: String, day: WorkDay, modifier: Modifier = Modifier, onCardClicked: (WorkDay) -> Unit) {
    val formatter = SimpleDateFormat("EEE\nd MMM", Locale.getDefault())
    Box {
        Card(modifier = modifier, onClick = { onCardClicked(day) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = formatter.format(day.date), textAlign = TextAlign.Center, fontSize = 14.sp)
            }
        }
        if (day.attendance.any { it.userId == userId }) {
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
        horizontalAlignment = CenterHorizontally
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
fun Avatar(teamMember: Member) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box {
            Image(
                painter = painterResource(R.drawable.man),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = teamMember.displayName)

    }
}

@Preview(showBackground = true)
@Composable
fun TeamScreenPreview() {
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

        val user = User("Vin", UserTeam("", "1", "My team")).apply { id = "1" }
        val team = Team("Some team", members = listOf(Member("1")))

        WhosInTheme {
            WhosInContent(uiState = WhosInViewModel.UiState.Success(user, team, workDays), onDayClicked = {})
        }

    }
}