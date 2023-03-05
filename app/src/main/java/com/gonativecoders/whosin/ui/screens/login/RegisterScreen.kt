package com.gonativecoders.whosin.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.composables.EmailField
import com.gonativecoders.whosin.ui.composables.NameField
import com.gonativecoders.whosin.ui.composables.PasswordField
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.gonativecoders.whosin.ui.screens.login.RegisterViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterScreen(
    onLoggedIn: (User) -> Unit,
    navigate: (route: String) -> Unit
) {
    val viewModel = getViewModel<RegisterViewModel>()

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
        NameField(value = uiState.displayName, onNewValue = viewModel::onNameChange)
        Spacer(modifier = Modifier.size(16.dp))
        EmailField(value = uiState.email, onNewValue = viewModel::onEmailChange)
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(value = uiState.password, onNewValue = viewModel::onPasswordChanged)
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = { viewModel.onCreateAccountClicked(onLoggedIn) }) {
            Text(text = "Create Account")
        }
        Spacer(modifier = Modifier.size(24.dp))
        TextButton(onClick = { navigate(MainDestinations.Login.route) }) {
            Text(text = "Already have an account? Login")
        }
        if (uiState.error != null) {
            Text(text = "Whoops! Something went wrong. ${uiState.error}")
        }
    }
}