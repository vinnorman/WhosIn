package com.gonativecoders.whosin.ui.home.jointeam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import com.gonativecoders.whosin.core.components.ErrorDialog
import com.gonativecoders.whosin.core.components.ScreenWithBackArrow
import com.gonativecoders.whosin.core.components.TextFieldWithIcon
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import org.koin.androidx.compose.getViewModel

@Composable
fun JoinTeamScreen(
    onJoinTeamSuccess: () -> Unit,
    onUserUpdated: (user: User) -> Unit,
    viewModel: JoinTeamViewModel = getViewModel(),
    onBackArrowPressed: () -> Unit
) {
    ScreenWithBackArrow(onBackArrowPressed = onBackArrowPressed) {
        JoinTeamContent(uiState = viewModel.uiState,
            onTeamIdChanged = viewModel::onTeamNameChanged,
            onJoinTeamClicked = {
                viewModel.onJoinTeamClicked(onUserUpdated)
            },
            onErrorDismissed = viewModel::onErrorDismissed
        )
    }
}

@Composable
fun JoinTeamContent(
    uiState: JoinTeamViewModel.UiState,
    onTeamIdChanged: (String) -> Unit,
    onJoinTeamClicked: () -> Unit,
    onErrorDismissed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextFieldWithIcon(
            value = uiState.teamId,
            onNewValue = onTeamIdChanged,
            placeholder = "Team Id",
            icon = Icons.Default.Group,
            contentDescription = "Team Id"
        )
        Spacer(modifier = Modifier.size(48.dp))
        Button(onClick = onJoinTeamClicked) {
            Text(text = "Join Team")
        }
        if (uiState.error != null) {
           ErrorDialog(exception = uiState.error, onDismissed = onErrorDismissed)
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
                onTeamIdChanged = {},
                onJoinTeamClicked = {},
                onErrorDismissed = {}
            )
        }

    }
}