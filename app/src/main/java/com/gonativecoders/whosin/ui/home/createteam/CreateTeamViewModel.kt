package com.gonativecoders.whosin.ui.home.createteam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.TeamRepository
import kotlinx.coroutines.launch

class CreateTeamViewModel(
    private val repository: TeamRepository,
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreRepository
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
                if (!isTeamNameAvailable) throw Exception("Team Id has been taken. Please choose another")
                val team = repository.createTeam(uiState.teamName, uiState.teamId)
                dataStore.putString(DataStoreRepository.TEAM_ID, team.id)
                onUserUpdated(authRepository.getCurrentUser())
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