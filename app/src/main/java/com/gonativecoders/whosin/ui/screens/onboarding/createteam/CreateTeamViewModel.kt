package com.gonativecoders.whosin.ui.screens.onboarding.createteam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.ui.MainDestinations

class CreateTeamViewModel(private val teamRepository: TeamRepository) : ViewModel() {

    data class UiState(
        val teamName: String = "",
        val loading: Boolean = false,
        val error: Throwable? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onTeamNameChanged(newValue: String) {
        uiState = uiState.copy(teamName = newValue)
    }

    fun onCreateTeamClicked(navigate: (route: String) -> Unit) {
        teamRepository.createTeam(uiState.teamName) { error ->
            if (error == null) {
                navigate(MainDestinations.Home.route)
            } else {
                uiState = uiState.copy(error = error)
            }
        }
    }

}