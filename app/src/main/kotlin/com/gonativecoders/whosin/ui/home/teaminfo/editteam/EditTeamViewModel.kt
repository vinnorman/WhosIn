package com.gonativecoders.whosin.ui.home.teaminfo.editteam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.data.team.TeamRepository
import kotlinx.coroutines.launch

class EditTeamViewModel(
    private val teamId: String,
    private val teamRepository: TeamRepository
) : ViewModel() {

    var uiState by mutableStateOf(UiState(loading = true))
        private set

    init {
        viewModelScope.launch {
            val team = teamRepository.getTeam(teamId)
            uiState = uiState.copy(teamName = team.name)
        }

    }

    data class UiState(
        val teamName: String = "",
        val loading: Boolean = false,
        val updating: Boolean = false,
        val error: Exception? = null
    )

    fun onTeamNameChanged(newValue: String) {
        uiState = uiState.copy(teamName = newValue)
    }

    suspend fun updateTeam() {
        teamRepository.editTeam(teamId, uiState.teamName)
    }

}