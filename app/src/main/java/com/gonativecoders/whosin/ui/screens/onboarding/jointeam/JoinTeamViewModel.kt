package com.gonativecoders.whosin.ui.screens.onboarding.jointeam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.ui.navigation.MainDestinations
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class JoinTeamViewModel(private val repository: TeamRepository) : ViewModel() {

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

    fun onJoinTeamClicked(navigate: (route: String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.joinTeam(uiState.teamCode)
                val user = Firebase.auth.currentUser
                navigate(MainDestinations.Home.route + "/${user!!.uid}")
            }catch (exception :Exception) {
                uiState = uiState.copy(error = exception)
            }
        }
    }

}