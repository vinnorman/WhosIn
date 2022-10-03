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
import com.gonativecoders.whosin.ui.composables.EmailField
import com.gonativecoders.whosin.ui.composables.NameField
import com.gonativecoders.whosin.ui.composables.PasswordField

@Composable
fun RegisterScreen(
    onLoggedIn: () -> Unit,
    moveToLoginScreen:  () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 96.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "Welcome Image")
        Spacer(modifier = Modifier.size(32.dp))
        NameField(value = "", onNewValue = {  })
        Spacer(modifier = Modifier.size(16.dp))
        EmailField(value = "", onNewValue = {  })
        Spacer(modifier = Modifier.size(16.dp))
        PasswordField(value = "", onNewValue = {  }, placeholder = R.string.password_field_placeholder)
        Spacer(modifier = Modifier.size(24.dp))
        Button(onClick = {  }) {
            Text(text = "Create Account")
        }
        Spacer(modifier = Modifier.size(24.dp))
        TextButton(onClick = { moveToLoginScreen()}) {
            Text(text = "Already have an account? Login")
        }
//        if (uiState.error != null) {
//            Text(text = "Whoops! Something went wrong. ${uiState.error}")
//        }
    }
}