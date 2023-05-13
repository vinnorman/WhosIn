package com.gonativecoders.whosin.ui.home.createteam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.ErrorDialog
import com.gonativecoders.whosin.core.components.layouts.StandardToolbarLayout
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateTeamScreen(
    viewModel: CreateTeamViewModel = getViewModel(),
    onUserUpdated: (user: User) -> Unit,
    onCreateTeamSuccess: () -> Unit,
    onBackArrowPressed: () -> Unit,
) {
    CreateTeamContent(
        uiState = viewModel.uiState,
        onTeamNameChanged = viewModel::onTeamNameChanged,
        onTeamIdChanged = viewModel::onTeamIdChanged,
        onCreateTeamClicked = {
            viewModel.onCreateTeamClicked(onUserUpdated, onCreateTeamSuccess)
        },
        onErrorDialogDismissed = viewModel::onErrorDialogDismissed,
        onBackArrowPressed = onBackArrowPressed
    )
}

@Composable
fun CreateTeamContent(
    uiState: CreateTeamViewModel.UiState,
    onTeamNameChanged: (String) -> Unit,
    onTeamIdChanged: (String) -> Unit,
    onCreateTeamClicked: () -> Unit,
    onErrorDialogDismissed: () -> Unit,
    onBackArrowPressed: () -> Unit
) {
    StandardToolbarLayout(
        onBackArrowPressed = onBackArrowPressed,
        title = "Create Team"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                OutlinedTextField(
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                    value = uiState.teamName,
                    onValueChange = onTeamNameChanged,
                    label = { Text("Team Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Group, contentDescription = null) }
                )
                Spacer(modifier = Modifier.size(24.dp))
                OutlinedTextField(
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                    value = uiState.teamId,
                    onValueChange = onTeamIdChanged,
                    label = { Text("Team Id") },
                    keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Group, contentDescription = null) }
                )
                Spacer(modifier = Modifier.size(48.dp))
            }

            Button(onClick = onCreateTeamClicked) {
                Text(text = "Create Team")
            }
            if (uiState.error != null) {
                ErrorDialog(exception = uiState.error, onDismissed = onErrorDialogDismissed)
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
            CreateTeamContent(
                uiState = CreateTeamViewModel.UiState(),
                onTeamNameChanged = {},
                onCreateTeamClicked = {},
                onTeamIdChanged = {},
                onErrorDialogDismissed = {},
                onBackArrowPressed = {}
            )
        }

    }
}