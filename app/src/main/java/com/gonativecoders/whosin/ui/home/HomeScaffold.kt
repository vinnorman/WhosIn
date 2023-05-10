@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.data.auth.model.UserTeam
import com.gonativecoders.whosin.ui.home.account.AccountScreen
import com.gonativecoders.whosin.ui.home.teammembers.TeamScreen
import com.gonativecoders.whosin.ui.home.whosin.WhosInScreen
import com.gonativecoders.whosin.ui.home.whosin.WhosInScreenPreview
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


    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            imageVector = ImageVector.vectorResource(id = R.drawable.toolbar_background),
            contentDescription = ""
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                HomeTopBar(user = user, navigate = navigate)
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
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = HomeDestinations.BottomNavDestination.WhosIn.route
                    ) {
                        composable(route = HomeDestinations.BottomNavDestination.WhosIn.route) {
                            WhosInScreen(
                                viewModel = getViewModel(parameters = { parametersOf(user) })
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
                                onJoinNewTeam = { navigate(HomeDestinations.JoinTeam.route) },
                                onEditProfile = { navigate(HomeDestinations.EditProfile.route) },
                            )
                        }
                    }

                }


            }


        )
    }

}

@Composable
private fun HomeTopBar(
    user: User,
    navigate: (String) -> Unit
) {

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier,
        title = {
            Text(
                text = user.team?.name ?: "Who's In",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = {
                navigate(HomeDestinations.TeamInfo.route)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Account Button",
                    tint = Color.White
                )
            }

        }
    )


}

@Preview
@Composable
private fun TopBarPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                imageVector = ImageVector.vectorResource(id = R.drawable.toolbar_background),
                contentDescription = ""
            )
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    HomeTopBar(
                        user = User("Vin", "", UserTeam(name = "Sky Bet"), "vin.norman@gmail.com"), navigate = {})
                },
                content = { innerPadding ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        WhosInScreenPreview()
                    }

                }


            )
        }
    }
}