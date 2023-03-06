package com.gonativecoders.whosin

import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.data.team.TeamService
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.WhosInService
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInViewModel
import com.gonativecoders.whosin.ui.screens.login.LoginViewModel
import com.gonativecoders.whosin.ui.screens.login.RegisterViewModel
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamViewModel
import com.gonativecoders.whosin.ui.screens.onboarding.jointeam.JoinTeamViewModel
import com.gonativecoders.whosin.ui.screens.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    viewModel { SplashViewModel(repository = get()) }
    viewModel { LoginViewModel(repository = get(), dataStore = get()) }
    viewModel { RegisterViewModel(repository = get()) }
    viewModel { CreateTeamViewModel(repository = get(), dataStore = get()) }
    viewModel { JoinTeamViewModel(repository = get()) }
    viewModel { (userId: String) -> WhosInViewModel(userId, repository = get()) }

    factory { AuthRepository(service = get()) }
    factory { WhosInRepository(service = get()) }
    factory { TeamRepository(service = get()) }

    single { WhosInService() }

    single { AuthService() }
    single { TeamService() }

    single { DataStoreRepository(androidContext()) }
}