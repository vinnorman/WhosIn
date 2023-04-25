@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.teaminfo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.ScreenWithBackArrow
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.ui.home.teaminfo.TeamInfoViewModel.UiState

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
    ScreenWithBackArrow(onBackArrowPressed = onBackArrowPressed) {
        Text(text = uiState.team.name!!)
        Spacer(modifier = Modifier.padding(20.dp))
        Row {
            Text(text = "Join Code")
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = uiState.team.code!!)
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
        val uiState = UiState.Success(Team(name = "Some team", code = "123ABC", members = listOf(Member("1"))))
        WhosInTheme {
            TeamInfoContent(uiState = uiState, onBackArrowPressed = { })
        }
    }
}