@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.teaminfo

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.components.Loading
import com.gonativecoders.whosin.core.components.ScreenWithBackArrow
import com.gonativecoders.whosin.core.theme.Grey500
import com.gonativecoders.whosin.core.theme.Grey900
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Member
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.ui.home.teaminfo.TeamInfoViewModel.UiState

@Composable
fun TeamInfoScreen(
    viewModel: TeamInfoViewModel,
    onBackArrowPressed: () -> Unit,
) {
    when (val uiState = viewModel.uiState) {
        is UiState.Error -> {}
        UiState.Loading -> Loading()
        is UiState.Success -> TeamInfoContent(
            uiState = uiState,
            onBackArrowPressed = onBackArrowPressed
        )
    }
}

@Composable
fun TeamInfoContent(uiState: UiState.Success, onBackArrowPressed: () -> Unit) {
    ScreenWithBackArrow(
        onBackArrowPressed = onBackArrowPressed,
        title = "Team Info"
    ) {
        Image(painter = painterResource(id = R.drawable.team), contentDescription = "Hi")

        Text(text = uiState.team.name!!, style = MaterialTheme.typography.headlineMedium)
        if (uiState.team.createdBy == uiState.user.id) {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Team Name")
            }
        }

        Spacer(modifier = Modifier.padding(20.dp))
        LabelAndValue(label = "Join Code", value = uiState.team.code!!)
        Spacer(modifier = Modifier.padding(20.dp))
        LabelAndValue(label = "Team Members", value = uiState.team.members.size.toString())
        uiState.admin?.let { admin ->
            Spacer(modifier = Modifier.padding(20.dp))
            LabelAndValue(label = "Created By", value = admin.displayName)
        } ?: run {
            Log.e("Team Info Screen", "Couldn't find admin for team")
        }
    }
}

@Composable
fun LabelAndValue(label: String, value: String) {
    Text(text = label, style = MaterialTheme.typography.bodySmall, color = Grey500)
    Spacer(modifier = Modifier.padding(2.dp))
    Text(text = value, style = MaterialTheme.typography.bodyLarge, color = Grey900)
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val team = Team(name = "Some team", createdBy = "123", code = "123ABC", members = listOf(Member("1")))
        val user = User("Vin").apply { id = "123" }
        val admin = Member("123", displayName = "The Admin Name")
        val uiState = UiState.Success(team, user, admin)
        WhosInTheme {
            TeamInfoContent(uiState = uiState, onBackArrowPressed = { })
        }
    }
}