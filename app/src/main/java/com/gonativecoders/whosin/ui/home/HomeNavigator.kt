package com.gonativecoders.whosin.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.home.account.EditProfileScreen
import com.gonativecoders.whosin.ui.home.createteam.CreateTeamScreen
import com.gonativecoders.whosin.ui.home.onboarding.jointeam.JoinTeamScreen
import com.gonativecoders.whosin.ui.home.teaminfo.TeamInfoScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

sealed class HomeDestinations(val route: String) {

    object TeamInfo : HomeDestinations("Team Info")
    object EditProfile : HomeDestinations("Edit Profile")
    object CreateTeam : HomeDestinations("Create Team")
    object JoinTeam : HomeDestinations("Join Team")
    object HomeScaffold : HomeDestinations("Bottom Bar Content")

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

@Composable
fun HomeNavigator(
    navController: NavHostController = rememberNavController(),
    user: User,
    onUserUpdated: (User) -> Unit,
    onLoggedOut: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = HomeDestinations.HomeScaffold.route
    ) {
        composable(route = HomeDestinations.HomeScaffold.route) {
            HomeScaffold(
                user = user,
                navigate = { route -> navController.navigate(route) },
                onLoggedOut = onLoggedOut
            )
        }
        composable(route = HomeDestinations.CreateTeam.route) {
            CreateTeamScreen(
                onUserUpdated = onUserUpdated,
                onCreateTeamSuccess = { navController.navigate(HomeDestinations.HomeScaffold.route) },
                onBackArrowPressed = { navController.popBackStack() }
            )
        }
        composable(route = HomeDestinations.JoinTeam.route) {
            JoinTeamScreen(
                onUserUpdated = onUserUpdated,
                onJoinTeamSuccess = { navController.navigate(HomeDestinations.HomeScaffold.route) },
                onBackArrowPressed = { navController.popBackStack() }
            )
        }
        composable(route = HomeDestinations.TeamInfo.route) {
            TeamInfoScreen(viewModel = getViewModel(parameters = { parametersOf(user) }),
                onBackArrowPressed = { navController.popBackStack() }
            )
        }
        composable(route = HomeDestinations.EditProfile.route) {
            EditProfileScreen(
                viewModel = getViewModel(parameters = { parametersOf(user) }),
                onCancel = { navController.popBackStack() },
                onUserUpdated = onUserUpdated
            )
        }
    }
}