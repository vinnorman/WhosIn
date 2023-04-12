package com.gonativecoders.whosin.ui.screens.onboarding.jointeam

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.composables.TextFieldWithIcon
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun JoinTeamScreen(
    navigate: (route: String) -> Unit,
    onUserUpdated: (user: User) -> Unit,
    viewModel: JoinTeamViewModel = getViewModel()
) {
    Column {
        JoinTeamContent(uiState = viewModel.uiState,
            onTeamNameChanged = viewModel::onTeamNameChanged,
            onJoinTeamClicked = {
                viewModel.onJoinTeamClicked(onUserUpdated, navigate)
            }
        )
    }
}

@Composable
fun JoinTeamContent(
    uiState: JoinTeamViewModel.UiState,
    onTeamNameChanged: (String) -> Unit,
    onJoinTeamClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 96.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TeamCodeField(value = uiState.teamCode, onNewValue = onTeamNameChanged)
        Spacer(modifier = Modifier.size(48.dp))
        Button(onClick = onJoinTeamClicked) {
            Text(text = "Join Team")
        }
        if (uiState.error != null) {
            Text(text = "Whoops! Something went wrong. ${uiState.error}")
        }

    }
}

@Composable
fun TeamCodeField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    TextFieldWithIcon(
        value = value,
        onNewValue = onNewValue,
        placeholder = "6 digit Team Code",
        icon = Icons.Default.Group,
        contentDescription = "6 digit Team Code"
    )

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            JoinTeamContent(
                uiState = JoinTeamViewModel.UiState(),
                onTeamNameChanged = {},
                onJoinTeamClicked = {}
            )
        }

    }
}