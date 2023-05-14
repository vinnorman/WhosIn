@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.teammembers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.GroupAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gonativecoders.whosin.core.components.ErrorView
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.UserPhoto
import com.gonativecoders.whosin.core.theme.Grey100
import com.gonativecoders.whosin.core.theme.Grey928
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User

@Composable
fun TeamScreen(
    viewModel: TeamViewModel
) {
    when (val uiState = viewModel.uiState) {
        is TeamViewModel.UiState.Error -> ErrorView(uiState.error.message)
        TeamViewModel.UiState.Loading -> Loading()
        is TeamViewModel.UiState.Success -> TeamContent(
            user = uiState.user,
            members = uiState.members,
        )
    }
}

@Composable
fun TeamContent(
    user: User,
    members: List<User>
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.GroupAdd, contentDescription = "Add Icon")
                    Text(
                        text = "Invite",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                }

            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        TeamView(user = user, members = members)
    }

}

@Composable
fun TeamView(
    user: User,
    members: List<User>
) {
    LazyColumn {
        items(members) { member ->
            TeamMemberView(user = member, member.id == user.id)
            Divider(color = Grey100, modifier = Modifier.padding(start = 20.dp, end = 20.dp))
        }
    }
}

@Composable
fun TeamMemberView(user: User, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserPhoto(user = user)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = if (isUser) user.name + " (You)" else user.name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Grey928
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TeamScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            TeamContent(
                user = User("Vin", "", email = "vin.norman@gmail.com").apply { id = "123" },
                members = listOf(
                    User("Vin", "", email = "vin.norman@gmail.com"),
                    User("Dave", "", email = "vin.norman@gmail.com"),
                    User("Graham", "", email = "vin.norman@gmail.com")
                ),

            )
        }
    }
}