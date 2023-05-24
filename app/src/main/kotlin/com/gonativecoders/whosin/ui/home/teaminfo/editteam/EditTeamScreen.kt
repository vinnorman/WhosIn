package com.gonativecoders.whosin.ui.home.teaminfo.editteam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.ErrorDialog
import com.gonativecoders.whosin.core.components.layouts.StandardToolbarLayout
import com.gonativecoders.whosin.core.theme.WhosInTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun EditTeamScreen(
    onBackArrowPressed: () -> Unit,
    onTeamUpdated: () -> Unit,
    viewModel: EditTeamViewModel = getViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {

    EditTeamContent(
        onBackArrowPressed = onBackArrowPressed,
        uiState = viewModel.uiState,
        onTeamNameChanged = viewModel::onTeamNameChanged,
        onSaveClicked = {
            coroutineScope.launch {
                viewModel.updateTeam()
                onTeamUpdated()
            }
        },
        onErrorDialogDismissed = {}
    )
}

@Composable
fun EditTeamContent(
    onBackArrowPressed: () -> Unit,
    uiState: EditTeamViewModel.UiState,
    onTeamNameChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
    onErrorDialogDismissed: () -> Unit,
) {
    StandardToolbarLayout(
        onBackArrowPressed = onBackArrowPressed,
        title = "Edit Team"
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 60.dp), contentAlignment = Alignment.Center
            ) {
                OutlinedTextField(
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                    value = uiState.teamName,
                    onValueChange = onTeamNameChanged,
                    label = { Text("Team Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                    leadingIcon = { Icon(imageVector = Icons.Filled.Group, contentDescription = null) }
                )

            }

            Button(onClick = onSaveClicked) {
                Text(text = "Save Changes")
            }
            if (uiState.error != null) {
                ErrorDialog(exception = uiState.error, onDismissed = onErrorDialogDismissed)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun EditTeamPreview() {
    WhosInTheme {
        EditTeamContent(
            onBackArrowPressed = { },
            uiState = EditTeamViewModel.UiState(),
            onTeamNameChanged = {},
            onSaveClicked = { },
            onErrorDialogDismissed = {})
    }
}
