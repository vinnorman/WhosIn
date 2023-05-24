package com.gonativecoders.whosin.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.auth.AuthManager
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.model.Team
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authManager: AuthManager,
    private val teamRepository: TeamRepository
) : ViewModel() {

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            val user: User? = authManager.getCurrentUser()
            if (user == null) {
                uiState = UiState.LoggedOut
            } else {
                teamRepository.getTeamFlow(user.currentTeamId ?: throw Exception("No team for user")).collectLatest { team ->
                    uiState = UiState.Success(user, team)
                }
            }
        }
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(val user: User, val team: Team) : UiState()

        object LoggedOut : UiState()

    }


}