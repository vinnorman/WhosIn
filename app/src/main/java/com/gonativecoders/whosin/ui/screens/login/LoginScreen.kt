package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.ui.composables.EmailField
import com.gonativecoders.whosin.ui.composables.PasswordField
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    navigate: (route: String) -> Unit,
    viewModel: LoginViewModel = getViewModel()
) {

    LoginContent(
        uiState = viewModel.uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChanged,
        onLoginClicked = { viewModel.onLoginClicked(navigate) },
        onCreateAccountClicked = { navigate(MainDestinations.Register.route) }
    )

}

@Composable
fun LoginContent(
    uiState: LoginViewModel.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 96.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            painter = painterResource(id = R.drawable.baseline_group_work_24),
            contentDescription = "Welcome Image"
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "Who's In",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(32.dp))
        EmailField(value = uiState.email, onNewValue = onEmailChange)
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(value = uiState.password, onNewValue = onPasswordChange)
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = onLoginClicked) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.size(24.dp))
        TextButton(onClick = onCreateAccountClicked) {
            Text(text = "Create Account")
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
            LoginContent(
                uiState = LoginViewModel.UiState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClicked = {},
                onCreateAccountClicked = {}
            )
        }

    }
}