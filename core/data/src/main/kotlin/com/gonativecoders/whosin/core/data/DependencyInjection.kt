package com.gonativecoders.whosin.core.data

import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.service.TeamService
import org.koin.dsl.module

val dataModule = module {

    factory { TeamRepository(service = get()) }

    single { TeamService() }
}