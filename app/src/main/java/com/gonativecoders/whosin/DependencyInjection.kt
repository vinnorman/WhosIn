package com.gonativecoders.whosin

import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.data.auth.FirebaseAuthService
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.FirestoreTeamService
import com.gonativecoders.whosin.data.team.TeamService
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.WhosInService
import com.gonativecoders.whosin.ui.screens.home.whosin.WhosInViewModel
import com.gonativecoders.whosin.ui.screens.login.LoginViewModel
import com.gonativecoders.whosin.ui.screens.login.RegisterViewModel
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    viewModel { LoginViewModel(authService = get(), dataStore = get()) }
    viewModel { RegisterViewModel(authService = get()) }
    viewModel { CreateTeamViewModel(teamService = get()) }
    viewModel { WhosInViewModel(repository = get()) }

    factory { WhosInRepository(service = get()) }

    single { WhosInService() }

    single<AuthService> { FirebaseAuthService() }
    single<TeamService> { FirestoreTeamService() }

    single { DataStoreRepository(androidContext()) }
}