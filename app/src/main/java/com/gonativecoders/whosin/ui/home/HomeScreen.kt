@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.home.account.AccountScreen
import com.gonativecoders.whosin.ui.home.teammembers.TeamScreen
import com.gonativecoders.whosin.ui.home.whosin.WhosInScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun HomeScreen(
    user: User,
    onLoggedOut: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {

                CenterAlignedTopAppBar(
                    title = {
                        val titleText = if (currentBackStackEntry?.destination?.route in teamTitleDestinations.map { it.route }) {
                            user.team?.name
                        } else {
                            currentBackStackEntry?.destination?.route
                        }  ?: "Who's In?"

                        Text(text = titleText)
                    },
                    actions = {

                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = "Account Button",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )

        },
        bottomBar = {
            NavigationBar {
                bottomDestinations.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.route) },
                        label = { Text(stringResource(screen.title)) },
                        selected = currentBackStackEntry?.destination?.route == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }

                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            HomeNavigation(
                navController = navController,
                innerPadding = innerPadding,
                user = user,
                onLoggedOut = onLoggedOut)
        }
    )
}

@Composable
private fun HomeNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues,
    user: User,
    onLoggedOut: () -> Unit
) {
    NavHost(
        navController = navController,
        modifier = Modifier.padding(innerPadding),

        startDestination = HomeDestinations.BottomNavDestination.WhosIn.route
    ) {
        composable(route = HomeDestinations.BottomNavDestination.WhosIn.route) {
            WhosInScreen(getViewModel(parameters = { parametersOf(user) }))
        }
        composable(route = HomeDestinations.BottomNavDestination.TeamMembers.route) {
            TeamScreen(getViewModel(parameters = { parametersOf(user) }))
        }
        composable(route = HomeDestinations.BottomNavDestination.Account.route) {
            AccountScreen(
                user = user,
                onLogOut = onLoggedOut,
                onCreateNewTeam = { navController.navigate(HomeDestinations.CreateTeam.route) },
                onJoinNewTeam = { navController.navigate(HomeDestinations.JoinTeam.route) }
            )
        }
        composable(route = HomeDestinations.CreateTeam.route) {
            Text(text = "Create Team")
        }
        composable(route = HomeDestinations.JoinTeam.route) {
            Text(text = "Join Team")
        }
    }
}
