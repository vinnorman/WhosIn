package com.gonativecoders.whosin.ui.home.onboarding.createteam

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.ScreenWithBackArrow
import com.gonativecoders.whosin.core.components.TeamNameField
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateTeamScreen(
    viewModel: CreateTeamViewModel = getViewModel(),
    onUserUpdated: (user: User) -> Unit,
    onCreateTeamSuccess: () -> Unit,
    onBackArrowPressed: () -> Unit
) {
    ScreenWithBackArrow(onBackArrowPressed = onBackArrowPressed) {
        CreateTeamContent(
            uiState = viewModel.uiState,
            onTeamNameChanged = viewModel::onTeamNameChanged,
            onCreateTeamClicked = {
                viewModel.onCreateTeamClicked(onUserUpdated, onCreateTeamSuccess)
            })
    }

}

@Composable
fun CreateTeamContent(
    uiState: CreateTeamViewModel.UiState,
    onTeamNameChanged: (String) -> Unit,
    onCreateTeamClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TeamNameField(value = uiState.teamName, onNewValue = onTeamNameChanged)
        Spacer(modifier = Modifier.size(48.dp))
        Button(onClick = onCreateTeamClicked) {
            Text(text = "Create Team")
        }
        if (uiState.error != null) {
            Text(text = "Whoops! Something went wrong. ${uiState.error}")
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            CreateTeamContent(
                uiState = CreateTeamViewModel.UiState(),
                onTeamNameChanged = {},
                onCreateTeamClicked = {}
            )
        }

    }
}