package com.gonativecoders.whosin.ui.screens.home.teaminfo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.ui.screens.home.teaminfo.TeamInfoViewModel.UiState
import com.gonativecoders.whosin.ui.screens.home.whosin.Loading
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun TeamInfoScreen(
    viewModel: TeamInfoViewModel
) {
    when (val uiState = viewModel.uiState) {
        is UiState.Error -> {}
        UiState.Loading -> Loading()
        is UiState.Success -> TeamInfoContent(uiState.team)
    }
}

@Composable
fun TeamInfoContent(team: Team) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = team.name!!)
        Spacer(modifier = Modifier.padding(20.dp))
        Row {
            Text(text = "Join Code")
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = team.code!!)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun TeamInfoScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val team = Team(name = "Some team", code = "123ABC", members = listOf(Member("1")))

        WhosInTheme {
            TeamInfoContent(team)
        }

    }
}