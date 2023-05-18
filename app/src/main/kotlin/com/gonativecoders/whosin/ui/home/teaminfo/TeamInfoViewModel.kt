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
import kotlinx.coroutines.launch

class TeamInfoViewModel(private val user: User, private val repository: TeamRepository) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            val team = repository.getTeam(user.currentTeamId?: throw Exception("No team ID found for user"))
            val teamMembers = repository.getTeamMembers(team.id)

            val admins = teamMembers.filter { it.id in team.admins }
            uiState = UiState.Success(
                team = team,
                members =  teamMembers,
                user = user,
                admins = admins)
        }
    }


    sealed class UiState {

        object Loading: UiState()

        data class Success(
            val team: Team,
            val members: List<TeamMember>,
            val user: User,
            val admins: List<TeamMember>
        ) : UiState()

        data class Error(val error: Throwable) : UiState()

    }
}