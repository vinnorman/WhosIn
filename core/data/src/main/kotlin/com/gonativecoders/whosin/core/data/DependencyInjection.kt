package com.gonativecoders.whosin.core.data

import com.gonativecoders.whosin.core.data.settings.SettingsRepository
import com.gonativecoders.whosin.core.data.settings.service.SettingsService
import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.service.TeamService
import com.gonativecoders.whosin.core.data.user.UserRepository
import com.gonativecoders.whosin.core.data.user.service.UserService
import com.gonativecoders.whosin.core.data.whosin.WhosInRepository
import com.gonativecoders.whosin.core.data.whosin.service.WhosInService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    factory { SettingsRepository(service = get()) }
    factory { TeamRepository(service = get()) }
    factory { WhosInRepository(service = get()) }
    factory { UserRepository(service = get()) }

    single { SettingsService(context = androidApplication()) }
    single { TeamService() }
    single { WhosInService() }
    single { UserService() }

}