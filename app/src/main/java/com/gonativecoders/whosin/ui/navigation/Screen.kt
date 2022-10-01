package com.gonativecoders.whosin.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val displayText: String, val icon: ImageVector) {
    object Login : Screen("login", "Login", Icons.Filled.Person)
    object WhosIn : Screen("whosin", "Who's In?", Icons.Filled.Search)
    object Me : Screen("me", "Me", Icons.Filled.DateRange)
    object Account : Screen("account", "Account", Icons.Filled.AccountCircle)
}