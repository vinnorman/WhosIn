package com.gonativecoders.whosin.ui.home.onboarding.profilesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import kotlinx.coroutines.launch

class ProfileSetupViewModel(private val dataStoreRepository: DataStoreRepository) : ViewModel() {


    fun onNextClicked() {
        viewModelScope.launch {
            dataStoreRepository.putBoolean(DataStoreRepository.HAS_COMPLETED_ONBOARDING, true)
        }
    }


}