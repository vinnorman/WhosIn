package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import kotlinx.coroutines.launch
import java.util.*

class WhosInViewModel(private val userId: String, private val repository: WhosInRepository) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        val user = repository.getUser(userId)
        val team = repository.getTeam(user.team?.id ?: throw Exception("No teams found!"))
        val workDays = repository.getWeek(team.id, Date())
        uiState = UiState.Success(user, team, workDays)
    }

    fun onDayClicked(day: WorkDay) {
        viewModelScope.launch {
            (uiState as? UiState.Success)?.let {
                repository.updateAttendance(userId, it.team.id, day)
                loadData()
            }
        }

    }


    sealed class UiState {

        object Loading: UiState()

        data class Success(
            val user: com.gonativecoders.whosin.data.auth.model.User,
            val team: Team,
            val workDays: List<WorkDay>,
        ) : UiState()

        data class Error(val error: Throwable) : UiState()

    }

}