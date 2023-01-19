package com.gonativecoders.whosin

import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.data.auth.FirebaseAuthService
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.FirestoreTeamService
import com.gonativecoders.whosin.data.team.TeamService
import com.gonativecoders.whosin.ui.screens.login.LoginViewModel
import com.gonativecoders.whosin.ui.screens.onboarding.createteam.CreateTeamViewModel
import com.gonativecoders.whosin.ui.screens.register.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    viewModel { LoginViewModel(authService = get(), dataStore = get()) }
    viewModel { RegisterViewModel(authService = get()) }
    viewModel { CreateTeamViewModel(teamService = get()) }

    single<AuthService> { FirebaseAuthService() }
    single<TeamService> { FirestoreTeamService() }

    single { DataStoreRepository(androidContext()) }
}