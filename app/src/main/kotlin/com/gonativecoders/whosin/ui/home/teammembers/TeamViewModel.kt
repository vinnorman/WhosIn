package com.gonativecoders.whosin.ui.home.teammembers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.model.Team
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TeamViewModel(private val user: User, private val repository: TeamRepository) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            try {
                val teamId = user.currentTeamId ?: throw Exception("No user team ID")
                repository.getTeamFlow(teamId).collectLatest { team ->
                    val members = repository.getTeamMembers(teamId)
                    uiState = UiState.Success(user, team, members)
                }
            } catch (exception: Exception) {
                uiState = UiState.Error(exception)
            }
        }
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(
            val user: User,
            val team: Team,
            val members: List<TeamMember>
        ) : UiState()

        data class Error(
            val error: Exception
        ) : UiState()

    }


}