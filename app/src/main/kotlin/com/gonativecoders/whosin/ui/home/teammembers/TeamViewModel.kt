package com.gonativecoders.whosin.ui.home.teammembers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.gonativecoders.whosin.data.auth.model.User
import kotlinx.coroutines.launch

class TeamViewModel(private val user: User, private val repository: TeamRepository) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            uiState = try {
                user.team?.id ?: throw Exception("No user team ID")
                val members = repository.getTeamMembers(user.team.id)
                UiState.Success(user,  members)
            } catch (exception: Exception) {
                UiState.Error(exception)
            }
        }
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(
            val user: User,
            val members: List<TeamMember>
        ) : UiState()

        data class Error(
            val error: Exception
        ) : UiState()

    }


}