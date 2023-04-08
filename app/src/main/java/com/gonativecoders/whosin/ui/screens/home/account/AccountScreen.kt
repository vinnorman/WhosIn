package com.gonativecoders.whosin.ui.screens.home.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.theme.WhosInTheme

@Composable
fun AccountScreen(
    user: User,
    onLogOut: () -> Unit,
    onCreateNewTeam: () -> Unit,
    onJoinNewTeam: () -> Unit
) {
    AccountContent(
        user = user,
        onLogOut = onLogOut,
        onCreateNewTeam = onCreateNewTeam,
        onJoinNewTeam = onJoinNewTeam
    )
}

@Composable
private fun AccountContent(
    user: User,
    onLogOut: () -> Unit,
    onCreateNewTeam: () -> Unit,
    onJoinNewTeam: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 96.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Your Account")
        Spacer(modifier = Modifier.padding(24.dp))
        Image(
            painter = painterResource(R.drawable.man),
            contentDescription = "Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Text(text = user.name)
        Button(onClick = { onLogOut() }) {
            Text(text = "Log Out")
        }
        Button(onClick = { onCreateNewTeam() }) {
            Text(text = "Create new team")
        }
        Button(onClick = { onJoinNewTeam() }) {
            Text(text = "Join A team")
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
            AccountScreen(
                user = User("Vin"),
                onLogOut = {},
                onCreateNewTeam = {},
                onJoinNewTeam = {}
            )
        }
    }
}