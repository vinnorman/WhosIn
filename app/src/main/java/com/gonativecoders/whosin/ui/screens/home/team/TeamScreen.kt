package com.gonativecoders.whosin.ui.screens.home.team

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.ui.common.ErrorView
import com.gonativecoders.whosin.ui.screens.home.whosin.Loading

@Composable
fun TeamScreen(
    viewModel: TeamViewModel
) {
    TeamContent(uiState = viewModel.uiState)
}

@Composable
fun TeamContent(
    uiState: TeamViewModel.UiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 12.dp, end = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is TeamViewModel.UiState.Error -> ErrorView(uiState.error.message)
            TeamViewModel.UiState.Loading -> Loading()
            is TeamViewModel.UiState.Success -> TeamView(user = uiState.user, team = uiState.team)
        }
    }
}

@Composable
fun TeamView(
    user: User,
    team: Team
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 96.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        team.members.forEach { member ->
            if (member.id == user.id) Text(text = "${member.displayName} (You)") else Text(text = member.displayName)
        }
    }
}

data class TeamScreenData(
    val user: User,
    val members: List<Member>
)