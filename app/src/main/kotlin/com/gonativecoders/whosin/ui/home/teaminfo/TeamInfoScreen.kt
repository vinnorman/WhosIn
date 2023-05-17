@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.teaminfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.layouts.StandardToolbarLayout
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.gonativecoders.whosin.core.theme.Grey500
import com.gonativecoders.whosin.core.theme.Grey900
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.ui.home.teaminfo.TeamInfoViewModel.UiState
import java.util.Date

@Composable
fun TeamInfoScreen(
    viewModel: TeamInfoViewModel,
    onBackArrowPressed: () -> Unit,
) {
    when (val uiState = viewModel.uiState) {
        is UiState.Error -> {}
        UiState.Loading -> Loading()
        is UiState.Success -> TeamInfoContent(
            uiState = uiState,
            onBackArrowPressed = onBackArrowPressed
        )
    }
}

@Composable
fun TeamInfoContent(uiState: UiState.Success, onBackArrowPressed: () -> Unit) {
    StandardToolbarLayout(
        onBackArrowPressed = onBackArrowPressed,
        title = "Team Info"
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.team), contentDescription = "Hi")

            Text(text = uiState.team.name, style = MaterialTheme.typography.headlineMedium)
            if (uiState.user.id in uiState.team.admins) {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Team Name")
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))
            LabelAndValue(label = "Team Id", value = uiState.team.id)
            Spacer(modifier = Modifier.padding(20.dp))
            LabelAndValue(label = "Team Members", value = uiState.members.size.toString())
            LabelAndValue(label = "Admins", value = uiState.admins.joinToString(separator = ", ") { it.name })

        }

    }
}

@Composable
fun LabelAndValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.bodySmall, color = Grey500)
    Spacer(modifier = Modifier.padding(2.dp))
    Text(text = value, style = MaterialTheme.typography.bodyLarge, color = Grey900)
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val team = com.gonativecoders.whosin.core.data.team.model.Team(
            name = "Some team",
            createdAt = Date(),
            admins = listOf("123", "234"),
            id = "my-team-id"
        )
        val user = User(id = "123", name = "Vin",  currentTeam = null, email = "vin.norman@gmail.com", hasSetupProfile = false)
        val admins = listOf(
            TeamMember(
                id = "123",
                name = "Some admin",
                email = "vin.norman@gmail.com",
                photoUri = null
            ),
            TeamMember(
                id = "234",
                name = "Another admin",
                email = "vin.norman@gmail.com",
                photoUri = null
            )
        )
        val uiState = UiState.Success(team = team, admins, user, admins)
        WhosInTheme {
            TeamInfoContent(uiState = uiState, onBackArrowPressed = { })
        }
    }
}