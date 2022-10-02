package com.gonativecoders.whosin.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun BottomNavigation(
    items: List<HomeDestinations>,
    currentRoute: String,
    onItemSelected: (String) -> Unit
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.route) },
                label = { Text(stringResource(screen.title)) },
                selected = currentRoute == screen.route,
                onClick = { onItemSelected(screen.route) }
            )
        }
    }
}