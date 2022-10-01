@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun LoginScreen(onLoggedIn: () -> Unit) {
    Column(modifier = Modifier.padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Username") })
        OutlinedTextField(value = "", onValueChange = {}, label = { Text(text = "Password") })
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