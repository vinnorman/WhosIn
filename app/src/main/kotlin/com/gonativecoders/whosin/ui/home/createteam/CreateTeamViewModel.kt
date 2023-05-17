package com.gonativecoders.whosin.ui.home.createteam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.team.TeamRepository
import kotlinx.coroutines.launch

class CreateTeamViewModel(
    private val repository: TeamRepository,
    private val authManager: AuthManager
) : ViewModel() {

    data class UiState(
        val teamName: String = "",
        val teamId: String = "",
        val loading: Boolean = false,
        val error: Exception? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onTeamNameChanged(newValue: String) {
        uiState = uiState.copy(teamName = newValue)
    }

    fun onCreateTeamClicked(onUserUpdated: (user: User) -> Unit, onCreateTeamSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val isTeamNameAvailable = repository.isTeamNameAvailable(uiState.teamId)
                val userId = authManager.getCurrentUser()?.id ?: throw Exception("Not currently logged in")
                if (!isTeamNameAvailable) throw Exception("Team Id has been taken. Please choose another")
                repository.createTeam(userId, uiState.teamName, uiState.teamId)
                onUserUpdated(authManager.getCurrentUser() ?: throw Exception("Not currently logged in"))
                onCreateTeamSuccess()
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

    fun onTeamIdChanged(newValue: String) {
        uiState = uiState.copy(teamId = newValue)
    }

    fun onErrorDialogDismissed() {
        uiState = uiState.copy(error = null)
    }

}