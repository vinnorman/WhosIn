package com.gonativecoders.whosin.ui.home.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gonativecoders.whosin.core.theme.Blue200
import com.gonativecoders.whosin.core.theme.Blue50
import com.gonativecoders.whosin.core.theme.Grey600
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.auth.model.UserTeam

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
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = user.photoUri,
                contentDescription = "User Profile Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium, color = Grey600)
        }

        Spacer(modifier = Modifier.size(20.dp))
        SettingOption(Icons.Default.Edit, "Edit Profile", onClick = {})
//        Spacer(modifier = Modifier.size(12.dp))
//        SettingOption(Icons.Default.Settings, "Settings", onClick = {})
        Spacer(modifier = Modifier.size(12.dp))
        SettingOption(Icons.Default.GroupAdd, "Create New Team", onClick = onCreateNewTeam)
        Spacer(modifier = Modifier.size(12.dp))
        SettingOption(Icons.Default.PersonAdd, "Join Team", onClick = onJoinNewTeam)
        Spacer(modifier = Modifier.size(12.dp))
        SettingOption(Icons.Default.Logout, "Logout", onClick = onLogOut)
    }
}


@Composable
fun SettingOption(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = text,
                onClick = onClick
            ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier
                    .background(Blue50, RoundedCornerShape(8.dp))
                    .padding(12.dp),
                tint = Blue200
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(text = text, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
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
                user = User("Vin", "", UserTeam("", "", ""), "vin.norman@gmail.com"),
                onLogOut = {},
                onCreateNewTeam = {},
                onJoinNewTeam = {}
            )
        }
    }
}