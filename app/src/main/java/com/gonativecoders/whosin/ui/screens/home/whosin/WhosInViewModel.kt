package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.model.Week
import kotlinx.coroutines.launch
import java.util.*

class WhosInViewModel(private val repository: WhosInRepository, private val dataStore: DataStoreRepository) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            val teamId = dataStore.getString(DataStoreRepository.TEAM_ID) ?: throw Exception("no team id found")
            val week = repository.getWeek(teamId, Date())
            uiState = uiState.copy(week = week)
        }
    }

    data class UiState(
        val week: Week? = null,
        val loading: Boolean = false,
        val error: Throwable? = null
    )


}