package com.gonativecoders.whosin.ui.home.jointeam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.components.ErrorDialog
import com.gonativecoders.whosin.core.components.TextFieldWithIcon
import com.gonativecoders.whosin.core.components.layouts.StandardToolbarLayout
import com.gonativecoders.whosin.core.theme.WhosInTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun JoinTeamScreen(
    onUserUpdated: (user: User) -> Unit,
    viewModel: JoinTeamViewModel = getViewModel(),
    onBackArrowPressed: () -> Unit
) {
    JoinTeamContent(
        uiState = viewModel.uiState,
        onTeamIdChanged = viewModel::onTeamIdChanged,
        onJoinTeamClicked = {
            viewModel.onJoinTeamClicked(onUserUpdated)
        },
        onErrorDismissed = viewModel::onErrorDismissed,
        onBackArrowPressed = onBackArrowPressed
    )
}

@Composable
fun JoinTeamContent(
    uiState: JoinTeamViewModel.UiState,
    onTeamIdChanged: (String) -> Unit,
    onJoinTeamClicked: () -> Unit,
    onErrorDismissed: () -> Unit,
    onBackArrowPressed: () -> Unit
) {

    StandardToolbarLayout(
        onBackArrowPressed = onBackArrowPressed,
        title = "Join Team"
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
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.None),
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
                onErrorDismissed = {},
                onBackArrowPressed = {}
            )
        }

    }
}