package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.ui.MainDestinations
import com.gonativecoders.whosin.ui.composables.EmailField
import com.gonativecoders.whosin.ui.composables.PasswordField
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    navigate:  (route: String) -> Unit,
    viewModel: LoginViewModel = getViewModel()
) {

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 96.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Welcome Image")
        Spacer(modifier = Modifier.size(32.dp))
        EmailField(value = uiState.email, onNewValue = viewModel::onEmailChange)
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(value = uiState.password, onNewValue = viewModel::onPasswordChanged, placeholder = R.string.password_field_placeholder)
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = { viewModel.onLoginClicked(navigate) }) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.size(24.dp))
        TextButton(onClick = { navigate(MainDestinations.Register.route) }) {
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
            LoginScreen(navigate = fun(_: String) {

            })
        }

    }
}