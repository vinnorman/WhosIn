package com.gonativecoders.whosin

import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.ui.MainViewModel
import com.gonativecoders.whosin.ui.auth.CreateAccountViewModel
import com.gonativecoders.whosin.ui.auth.LoginViewModel
import com.gonativecoders.whosin.ui.home.account.EditProfileViewModel
import com.gonativecoders.whosin.ui.home.createteam.CreateTeamViewModel
import com.gonativecoders.whosin.ui.home.jointeam.JoinTeamViewModel
import com.gonativecoders.whosin.ui.home.onboarding.profilesetup.ProfileSetupViewModel
import com.gonativecoders.whosin.ui.home.teaminfo.TeamInfoViewModel
import com.gonativecoders.whosin.ui.home.teammembers.TeamViewModel
import com.gonativecoders.whosin.ui.home.whosin.WhosInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModules = module {
    viewModel { MainViewModel(authManager = get())}

    viewModel { (user: User) -> ProfileSetupViewModel(user = user, authManager = get()) }
    viewModel { LoginViewModel(authManager = get()) }
    viewModel { CreateAccountViewModel(authManager = get()) }
    viewModel { CreateTeamViewModel(repository = get(), authManager = get()) }
    viewModel { JoinTeamViewModel(repository = get(), authManager = get()) }
    viewModel { (user: User) -> TeamViewModel(user = user, repository = get()) }
    viewModel { (user: User) -> WhosInViewModel(user = user, whosInRepository = get(), teamRepository = get()) }

    viewModel { (user: User) -> TeamInfoViewModel(user = user, repository = get()) }
    viewModel { (user: User) -> EditProfileViewModel(user = user, authManager = get()) }
}