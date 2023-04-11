package com.gonativecoders.whosin.ui.screens.onboarding.jointeam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import kotlinx.coroutines.launch

class JoinTeamViewModel(
    private val repository: TeamRepository,
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreRepository
) : ViewModel() {

    data class UiState(
        val teamCode: String = "",
        val loading: Boolean = false,
        val error: Throwable? = null
    )

    var uiState by mutableStateOf(UiState())
        private set

    fun onTeamNameChanged(newValue: String) {
        uiState = uiState.copy(teamCode = newValue)
    }

    fun onJoinTeamClicked(onUserUpdated: (user: User) -> Unit, navigate: (route: String) -> Unit) {
        viewModelScope.launch {
            try {
                val team = repository.joinTeam(uiState.teamCode)
                dataStore.putString(DataStoreRepository.TEAM_ID, team.id)
                dataStore.putBoolean(DataStoreRepository.HAS_SEEN_ONBOARDING, true)
                onUserUpdated(authRepository.getCurrentUser())
                navigate(MainDestinations.Home.route)
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

}