package com.gonativecoders.whosin.ui.screens.home.whosin

import androidx.lifecycle.ViewModel
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.model.Week
import java.util.*

class WhosInViewModel(private val repository: WhosInRepository) : ViewModel() {

    suspend fun getWeek(): Week? {
        return repository.getWeek("", Date())
    }


}