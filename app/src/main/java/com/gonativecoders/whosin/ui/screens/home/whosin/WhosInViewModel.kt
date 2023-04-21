package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.team.model.Team
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import kotlinx.coroutines.launch
import java.util.*

class WhosInViewModel(private val user: User, private val repository: WhosInRepository) : ViewModel() {

    var selectedWeek: Calendar = Calendar.getInstance()

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        uiState = try {
            val team = repository.getTeam(user.team?.id ?: throw Exception("No teams found!"))
            val workDays = repository.getWeek(team.id, selectedWeek.time)
            UiState.Success(user, team, workDays)
        } catch (exception: Exception) {
            UiState.Error(exception)
        }

    }

    fun goToNextWeek() {
        selectedWeek.add(Calendar.WEEK_OF_YEAR, 1)
        viewModelScope.launch {
            loadData()
        }
    }

    fun goToPreviousWeek() {
        selectedWeek.add(Calendar.WEEK_OF_YEAR, -1)
        viewModelScope.launch {
            loadData()
        }
    }

    fun updateAttendance(day: WorkDay) {
        (uiState as? UiState.Success)?.let {
            viewModelScope.launch {
                repository.updateAttendance(user.id, it.team.id, day)
                loadData()
            }
            toggleAttendance(it, day)
        }
    }

    private fun toggleAttendance(
        it: UiState.Success,
        day: WorkDay
    ) {
        uiState = it.copy(workDays = it.workDays.map { workDay ->
            if (day == workDay) {

                workDay.copy(attendance = workDay.attendance.toMutableList().apply {
                    val wasRemoved = removeIf { attendee -> attendee.userId == user.id }
                    if (!wasRemoved) add(Attendee(user.id))
                }).apply {
                    id = workDay.id
                }
            } else {
                workDay
            }
        })
    }

    fun goToToday() {
        selectedWeek = Calendar.getInstance()
        viewModelScope.launch {
            loadData()
        }
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(
            val user: User,
            val team: Team,
            val workDays: List<WorkDay>,
        ) : UiState()

        data class Error(val error: Throwable) : UiState()

    }

}