package com.gonativecoders.whosin.ui.home.jointeam

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

class JoinTeamViewModel(
    private val repository: TeamRepository,
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreRepository
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

    fun onJoinTeamClicked(onUserUpdated: (user: User) -> Unit) {
        viewModelScope.launch {
            try {
                val team = repository.joinTeam(uiState.teamId)
                dataStore.putString(DataStoreRepository.TEAM_ID, team.id)
                onUserUpdated(authRepository.getCurrentUser())
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

    fun onErrorDismissed() {
        uiState = uiState.copy(error = null)
    }

}