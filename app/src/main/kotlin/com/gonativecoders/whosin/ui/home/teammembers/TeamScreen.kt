package com.gonativecoders.whosin.ui.home.teammembers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.components.ErrorView
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.UserPhoto
import com.gonativecoders.whosin.core.data.team.model.Team
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.gonativecoders.whosin.core.theme.Grey100
import com.gonativecoders.whosin.core.theme.Grey928
import com.gonativecoders.whosin.core.theme.WhosInTheme

@Composable
fun TeamScreen(
    viewModel: TeamViewModel
) {
    when (val uiState = viewModel.uiState) {
        is TeamViewModel.UiState.Error -> ErrorView(uiState.error.message)
        TeamViewModel.UiState.Loading -> Loading()
        is TeamViewModel.UiState.Success -> TeamContent(
            user = uiState.user,
            team = uiState.team,
            members = uiState.members,
        )
    }
}

@Composable
fun TeamContent(
    user: User,
    team: Team,
    members: List<TeamMember>
) {

    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    context.invite(team.name)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.GroupAdd, contentDescription = "Add Icon")
                    Text(
                        text = "Invite",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TeamView(user = user, members = members)
    }

}

@Composable
fun TeamView(
    user: User,
    members: List<TeamMember>
) {
    LazyColumn {
        items(members) { member ->
            TeamMemberView(teamMember = member, member.id == user.id)
            Divider(color = Grey100, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        }
    }
}

@Composable
fun TeamMemberView(teamMember: TeamMember, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserPhoto(photoUri = teamMember.photoUri)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = if (isUser) teamMember.name + " (You)" else teamMember.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Grey928
        )
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
                user = User(
                    id = "123",
                    name = "Vin",
                    currentTeamId = "123",
                    teams = listOf(),
                    email ="vin.norman@gmail.com",
                    hasSetupProfile = false
                ),
                team = Team("123", listOf(), "Team Name"),
                members = listOf(
                    TeamMember(
                        id = "123",
                        "Vin",
                        email = "vin.norman@gmail.com",
                        photoUri = null
                    ),
                    TeamMember(
                        id = "123",
                        "Vin",
                        email = "vin.norman@gmail.com",
                        photoUri = null
                    ),
                    TeamMember(
                        id = "123",
                        "Vin",
                        email = "vin.norman@gmail.com",
                        photoUri = null
                    ),
                ),

                )
        }
    }
}