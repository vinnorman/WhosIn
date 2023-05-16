package com.gonativecoders.whosin.ui.home.whosin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.model.TeamMember
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.model.Attendee
import com.gonativecoders.whosin.data.whosin.model.WorkDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class WhosInViewModel(private val user: User, private val whosInRepository: WhosInRepository, private val teamRepository: TeamRepository) : ViewModel() {

    private var selectedWeek = Calendar.getInstance()

    private val teamId: String? = user.team?.id

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        viewModelScope.launch {
            loadData()
        }
    }

    private suspend fun loadData() {
        try {
            val members = teamRepository.getTeamMembers(teamId ?: throw Exception("No teams found!"))
            val flow: Flow<List<WorkDay>> = whosInRepository.getWeek(teamId, selectedWeek.time)
            flow.collect { workDays ->
                if (workDays.isNotEmpty()) {
                    uiState = UiState.Success(
                        user = user,
                        members = members,
                        workDays = workDays
                    )
                }
            }
        } catch (exception: Exception) {
            uiState = UiState.Error(exception)
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
        teamId ?: return
        (uiState as? UiState.Success)?.let {
            viewModelScope.launch {
                whosInRepository.updateAttendance(user.id, teamId, day)
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

    fun goToDate(date: Date) {
        selectedWeek = Calendar.getInstance().apply { time = date }
        viewModelScope.launch {
            loadData()
        }
    }

    sealed class UiState {

        object Loading : UiState()

        data class Success(
            val user: User,
            val members: List<TeamMember>,
            val workDays: List<WorkDay>,
        ) : UiState()

        data class Error(val error: Throwable) : UiState()

    }

}