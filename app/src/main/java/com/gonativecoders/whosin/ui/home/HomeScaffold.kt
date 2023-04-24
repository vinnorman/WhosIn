@file:OptIn(ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun HomeScaffold(
    user: User,
    navigate: (String) -> Unit,
    onLoggedOut: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = currentBackStackEntry?.destination?.route != HomeDestinations.BottomNavDestination.Account.route,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column {
                            Text(
                                text = "Who's In",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = user.team?.name ?: "No team",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                },
                actions = {
                    AnimatedVisibility(
                        visible = currentBackStackEntry?.destination?.route != HomeDestinations.BottomNavDestination.Account.route,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        IconButton(onClick = {
                            navigate(HomeDestinations.TeamInfo.route)
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Account Button",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
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
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                startDestination = HomeDestinations.BottomNavDestination.WhosIn.route
            ) {
                composable(route = HomeDestinations.BottomNavDestination.WhosIn.route) {
                    WhosInScreen(
                        viewModel = getViewModel(parameters = { parametersOf(user) }),
                        navigate = navigate
                    )
                }
                composable(route = HomeDestinations.BottomNavDestination.TeamMembers.route) {
                    TeamScreen(
                        viewModel = getViewModel(parameters = { parametersOf(user) }),
                        navigate = navigate
                    )
                }
                composable(route = HomeDestinations.BottomNavDestination.Account.route) {
                    AccountScreen(
                        user = user,
                        onLogOut = onLoggedOut,
                        onCreateNewTeam = { navigate(HomeDestinations.CreateTeam.route) },
                        onJoinNewTeam = { navigate(HomeDestinations.JoinTeam.route) }
                    )
                }
            }
        }
    )

}