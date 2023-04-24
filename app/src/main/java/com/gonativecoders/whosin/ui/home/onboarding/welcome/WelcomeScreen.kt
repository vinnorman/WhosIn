package com.gonativecoders.whosin.ui.home.onboarding.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.ui.home.HomeDestinations

@Composable
fun WelcomeScreen(navigate: (route: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(onClick = { navigate(HomeDestinations.CreateTeam.route) }) {
            Text(text = "Create new team")
        }
        Spacer(modifier = Modifier.size(24.dp))
        TextButton(onClick = { navigate(HomeDestinations.JoinTeam.route) }) {
            Text(text = "Join a team")
        }
    }
}