package com.gonativecoders.whosin.ui.home.jointeam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.data.team.TeamRepository
import kotlinx.coroutines.launch

class JoinTeamViewModel(
    private val repository: TeamRepository,
    private val authManager: AuthManager
) : ViewModel() {

    data class UiState(
        val teamId: String = "",
        val loading: Boolean = false,
        val error: Exception? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onTeamNameChanged(newValue: String) {
        uiState = uiState.copy(teamId = newValue)
    }

    fun onJoinTeamClicked(onUserUpdated: (user: com.gonativecoders.whosin.core.auth.model.User) -> Unit) {
        viewModelScope.launch {
            try {
                val userId = authManager.getCurrentUser()?.id ?: throw Exception("Not currently logged in")
                repository.joinTeam(userId, uiState.teamId)
                onUserUpdated(authManager.getCurrentUser() ?: throw Exception("Not currently logged in"))
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

    fun onErrorDismissed() {
        uiState = uiState.copy(error = null)
    }

}