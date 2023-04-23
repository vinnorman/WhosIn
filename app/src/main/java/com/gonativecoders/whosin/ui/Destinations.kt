package com.gonativecoders.whosin.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.gonativecoders.whosin.R

sealed class MainDestinations(val route: String) {
    object Splash : MainDestinations("splash")
    object Login : MainDestinations("login")
    object Register : MainDestinations("register")
    object Home : MainDestinations("home")
    object Onboarding: MainDestinations("onboarding")

    override fun toString(): String {
        return route
    }
}

sealed class HomeDestinations(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    object WhosIn : HomeDestinations("whosin", R.string.screen_name_whos_in, Icons.Filled.Search)
    object Team : HomeDestinations("team", R.string.screen_name_team, Icons.Filled.Group)
    object TeamInfo : HomeDestinations("team-info", R.string.screen_name_team, Icons.Filled.Group)
    object Account : HomeDestinations("account", R.string.screen_name_account, Icons.Filled.AccountCircle)
}

sealed class OnboardingDestinations(val route: String) {
    object Welcome : OnboardingDestinations("welcome")
    object CreateTeam : OnboardingDestinations("create-team")
    object JoinTeam : OnboardingDestinations("join-team")
}