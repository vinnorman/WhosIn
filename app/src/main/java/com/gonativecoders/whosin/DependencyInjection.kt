package com.gonativecoders.whosin

import com.gonativecoders.whosin.data.auth.AuthRepository
import com.gonativecoders.whosin.data.auth.AuthService
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.datastore.DataStoreRepository
import com.gonativecoders.whosin.data.team.TeamRepository
import com.gonativecoders.whosin.data.team.TeamService
import com.gonativecoders.whosin.data.whosin.WhosInRepository
import com.gonativecoders.whosin.data.whosin.WhosInService
import com.gonativecoders.whosin.ui.MainViewModel
import com.gonativecoders.whosin.ui.auth.CreateAccountViewModel
import com.gonativecoders.whosin.ui.auth.LoginViewModel
import com.gonativecoders.whosin.ui.home.account.EditProfileViewModel
import com.gonativecoders.whosin.ui.home.onboarding.createteam.CreateTeamViewModel
import com.gonativecoders.whosin.ui.home.onboarding.jointeam.JoinTeamViewModel
import com.gonativecoders.whosin.ui.home.onboarding.profilesetup.ProfileSetupViewModel
import com.gonativecoders.whosin.ui.home.teaminfo.TeamInfoViewModel
import com.gonativecoders.whosin.ui.home.teammembers.TeamViewModel
import com.gonativecoders.whosin.ui.home.whosin.WhosInViewModel
import com.gonativecoders.whosin.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    viewModel { MainViewModel()}

    viewModel { SplashViewModel(repository = get(), dataStoreRepository = get()) }
    viewModel { (user: User) -> ProfileSetupViewModel(user = user, authRepository = get()) }
    viewModel { LoginViewModel(repository = get(), dataStore = get()) }
    viewModel { CreateAccountViewModel(repository = get()) }
    viewModel { CreateTeamViewModel(repository = get(), dataStore = get(), authRepository = get()) }
    viewModel { JoinTeamViewModel(repository = get(), dataStore = get(), authRepository = get()) }
    viewModel { (user: User) -> TeamViewModel(user = user, repository = get()) }
    viewModel { (user: User) -> WhosInViewModel(user = user, repository = get()) }

    viewModel { (user: User) -> TeamInfoViewModel(user = user, repository = get()) }
    viewModel { (user: User) -> EditProfileViewModel(user = user, authRepository = get()) }

    factory { AuthRepository(service = get()) }
    factory { WhosInRepository(service = get()) }
    factory { TeamRepository(service = get()) }

    single { WhosInService() }

    single { AuthService() }
    single { TeamService() }

    single { DataStoreRepository(androidContext()) }
}