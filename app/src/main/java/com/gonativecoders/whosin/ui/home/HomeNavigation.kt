package com.gonativecoders.whosin.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.gonativecoders.whosin.R

sealed class HomeDestinations(val route: String) {

    object TeamInfo : HomeDestinations("Team Info")
    object CreateTeam : HomeDestinations("Create Team")
    object JoinTeam : HomeDestinations("Join Team")


    sealed class BottomNavDestination(route: String, @StringRes val title: Int, val icon: ImageVector) : HomeDestinations(route) {
        object WhosIn : BottomNavDestination("Who's In", R.string.screen_name_whos_in, Icons.Filled.Search)
        object TeamMembers : BottomNavDestination("Team Members", R.string.screen_name_team, Icons.Filled.Group)
        object Account : BottomNavDestination("Account", R.string.screen_name_account, Icons.Filled.AccountCircle)
    }

}

val bottomDestinations = listOf(
    HomeDestinations.BottomNavDestination.WhosIn,
    HomeDestinations.BottomNavDestination.TeamMembers,
    HomeDestinations.BottomNavDestination.Account
)

val topBarDestinations = listOf(
    HomeDestinations.BottomNavDestination.WhosIn,
    HomeDestinations.BottomNavDestination.TeamMembers
)

fun NavBackStackEntry?.isBottomNavDestination(): Boolean {
    this ?: return false
    return this.destination.route in bottomDestinations.map { it.route }
}

fun NavBackStackEntry?.shouldShowTopBar(): Boolean {
    this ?: return false
    return this.destination.route in topBarDestinations.map { it.route }
}


