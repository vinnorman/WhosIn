package com.gonativecoders.whosin.ui.home.teaminfo

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

class TeamInfoViewModel(private val user: User, private val repository: TeamRepository) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    private val teamId = user.currentTeamId ?: throw Exception("No team ID found for user")

    init {
        viewModelScope.launch {
            repository.getTeamFlow(teamId).collectLatest { team ->
                val teamMembers = repository.getTeamMembers(team.id)

                val admins = teamMembers.filter { teamMember -> teamMember.id in team.admins }
                uiState = UiState.Success(
                    team = team,
                    members = teamMembers,
                    user = user,
                    admins = admins
                )
            }

        }
    }

    suspend fun leaveTeam() {
        repository.leaveTeam(user.id, teamId)
    }


    sealed class UiState {

        object Loading : UiState()

        data class Success(
            val team: Team,
            val members: List<TeamMember>,
            val user: User,
            val admins: List<TeamMember>
        ) : UiState()

        data class Error(val error: Throwable) : UiState()

    }
}