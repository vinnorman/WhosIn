package com.gonativecoders.whosin.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.components.AppLogo
import com.gonativecoders.whosin.core.components.EmailField
import com.gonativecoders.whosin.core.components.ErrorDialog
import com.gonativecoders.whosin.core.components.NameField
import com.gonativecoders.whosin.core.components.PasswordField
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import org.koin.androidx.compose.getViewModel

@Composable
fun CreateAccountScreen(
    onAccountCreated: (User) -> Unit,
    navigateToLoginScreen: () -> Unit
) {
    val viewModel = getViewModel<CreateAccountViewModel>()

    val uiState = viewModel.uiState

    CreateAccountContent(
        onNameChange = viewModel::onNameChange,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChanged,
        onCreateAccountClicked = { viewModel.onCreateAccountClicked(onAccountCreated) },
        uiState = uiState,
        navigateToLoginScreen = navigateToLoginScreen,
        onErrorDialogDismissed = viewModel::onErrorDialogDismissed

    )

}

@Composable
fun CreateAccountContent(
    uiState: CreateAccountViewModel.UiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCreateAccountClicked: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    onErrorDialogDismissed: () -> Unit
) {
    if (uiState.error != null) {
        ErrorDialog(
            exception = uiState.error,
            onDismissed = onErrorDialogDismissed
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)) {
            AppLogo(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(3f)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.login_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                NameField(value = uiState.displayName, onNewValue = onNameChange)
                Spacer(modifier = Modifier.size(16.dp))
                EmailField(value = uiState.email, onNewValue = onEmailChange)
                Spacer(modifier = Modifier.size(16.dp))
                PasswordField(value = uiState.password, onNewValue = onPasswordChange)
                Spacer(modifier = Modifier.size(24.dp))
                OutlinedButton(
                    onClick = onCreateAccountClicked,
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.size(height = 48.dp, width = 200.dp)
                ) {
                    if (uiState.isCreatingAccount) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(text = "Create Account", modifier = Modifier.padding(6.dp), color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))
                TextButton(onClick = navigateToLoginScreen) {
                    Text(text = "Already have an account? Click here", color = Color.White)
                }
            }

        }

    }

}

@Preview(showBackground = true)
@Composable
private fun Portrait() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            CreateAccountContent(
                uiState = CreateAccountViewModel.UiState(),
                onEmailChange = {},
                onNameChange = {},
                onPasswordChange = {},
                onCreateAccountClicked = {},
                navigateToLoginScreen = {},
                onErrorDialogDismissed = {}
            )
        }
    }
}