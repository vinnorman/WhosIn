package com.gonativecoders.whosin.ui.home.onboarding.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.layouts.StandardToolbarLayout
import com.gonativecoders.whosin.core.theme.WhosInTheme

@Composable
fun WelcomeScreen(
    navigateToCreateTeamScreen: () -> Unit,
    navigateToJoinTeamScreen: () -> Unit
) {
        StandardToolbarLayout(
            title = "Welcome!"
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = navigateToCreateTeamScreen) {
                    Text(text = "Create new team")
                }
                Spacer(modifier = Modifier.size(24.dp))
                TextButton(onClick = navigateToJoinTeamScreen) {
                    Text(text = "Join a team")
                }
            }

        }
    
}

@Preview(showBackground = true)
@Composable
private fun WelcomeScreenPreview() {
    WhosInTheme() {
        WelcomeScreen(navigateToCreateTeamScreen = {  }) {

        }
    }
}