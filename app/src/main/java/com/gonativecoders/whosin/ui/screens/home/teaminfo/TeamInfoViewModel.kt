package com.gonativecoders.whosin.ui.screens.home.teaminfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.data.team.model.Team
import kotlinx.coroutines.launch

class TeamInfoViewModel(private val user: User, private val repository: TeamRepository) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            val team = repository.getTeam(user.team?.id ?: throw Exception("No team ID found for user"))
            uiState = UiState.Success(team)
        }
    }


    sealed class UiState {

        object Loading: UiState()

        data class Success(
            val team: Team
        ) : UiState()

        data class Error(val error: Throwable) : UiState()

    }
}