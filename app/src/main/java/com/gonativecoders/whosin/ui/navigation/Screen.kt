package com.gonativecoders.whosin.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Login : Screen("login")

    sealed class BottomNavScreen(route: String, val displayText: String, val icon: ImageVector) : Screen(route) {
        object WhosIn : BottomNavScreen("whosin", "Who's In?", Icons.Filled.Search)
        object Me : BottomNavScreen("me", "Me", Icons.Filled.DateRange)
        object Account : BottomNavScreen("account", "Account", Icons.Filled.AccountCircle)
    }

}