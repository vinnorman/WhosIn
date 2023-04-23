package com.gonativecoders.whosin.ui.home.teammembers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.ErrorView
import com.gonativecoders.whosin.core.components.InitialsCircle
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.ui.home.whosin.Loading

@Composable
fun TeamScreen(
    viewModel: TeamViewModel
) {
    when (val uiState = viewModel.uiState) {
        is TeamViewModel.UiState.Error -> ErrorView(uiState.error.message)
        TeamViewModel.UiState.Loading -> Loading()
        is TeamViewModel.UiState.Success -> TeamContent(user = uiState.user, team = uiState.team)
    }
}

@Composable
fun TeamContent(
    user: User,
    team: Team
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TeamView(user = user, team = team)
    }
}

@Composable
fun TeamView(
    user: User,
    team: Team
) {
    LazyColumn {
        items(team.members) { member ->
            TeamMemberView(member = member, member.id == user.id)
        }
    }
}

@Composable
fun TeamMemberView(member: Member, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InitialsCircle(name = member.displayName, member.initialsColor)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = if (isUser) member.displayName + " (You)" else member.displayName)
    }
}

@Preview(showBackground = true)
@Composable
fun TeamScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            TeamContent(
                user = User(name = "Vin").apply { id = "123" },
                team = Team("Some team", members = listOf(Member(id = "123", displayName = "Vin", initialsColor = "FF0000"), Member(id = "234", displayName = "Maria Hampson", initialsColor = "FF0000")))
            )
        }
    }
}