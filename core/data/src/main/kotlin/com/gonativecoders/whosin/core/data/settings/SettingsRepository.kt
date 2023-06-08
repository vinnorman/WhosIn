package com.gonativecoders.whosin.core.data.settings

import com.gonativecoders.whosin.core.data.settings.service.SettingsService

class SettingsRepository internal constructor(private val service: SettingsService){

    suspend fun setTutorialComplete() {
        service.putBoolean(SettingsService.HAS_COMPLETED_TUTORIAL, true)
    }

    suspend fun hasCompletedTutorial(): Boolean {
        return service.getBoolean(SettingsService.HAS_COMPLETED_TUTORIAL)
    }

}