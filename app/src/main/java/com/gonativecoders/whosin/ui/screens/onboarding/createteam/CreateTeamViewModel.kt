package com.gonativecoders.whosin.ui.screens.onboarding.createteam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class CreateTeamViewModel(private val repository: TeamRepository, private val dataStore: DataStoreRepository) : ViewModel() {

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
        viewModelScope.launch {
            try {
                val team = repository.createTeam(uiState.teamName)
                dataStore.putString(DataStoreRepository.TEAM_ID, team.id)
                dataStore.putBoolean(DataStoreRepository.HAS_SEEN_ONBOARDING, true)
                val user = Firebase.auth.currentUser
                navigate(MainDestinations.Home.route)
            }catch (exception :Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

}