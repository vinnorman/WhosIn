package com.gonativecoders.whosin.core.data

import com.gonativecoders.whosin.core.data.team.TeamRepository
import com.gonativecoders.whosin.core.data.team.service.TeamService
import com.gonativecoders.whosin.core.data.whosin.WhosInRepository
import com.gonativecoders.whosin.core.data.whosin.service.WhosInService
import org.koin.dsl.module

val dataModule = module {

    factory { TeamRepository(service = get()) }
    factory { WhosInRepository(service = get()) }

    single { TeamService() }
    single { WhosInService() }

}