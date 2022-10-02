@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.ui.composables.EmailField
import com.gonativecoders.whosin.ui.composables.PasswordField
import com.gonativecoders.whosin.ui.theme.WhosInTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(onLoggedIn: () -> Unit) {
    val viewModel = getViewModel<LoginViewModel>()

    val uiState = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmailField(value = uiState.value.email, onNewValue = viewModel::onEmailChange)
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(value = uiState.value.password, onNewValue = viewModel::onPasswordChanged, placeholder = R.string.password_field_placeholder)
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = onLoggedIn) {
            Text(text = "Log In")
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
            LoginScreen {

            }
        }

    }
}