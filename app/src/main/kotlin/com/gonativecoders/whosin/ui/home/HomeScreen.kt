package com.gonativecoders.whosin.ui.home

import androidx.compose.runtime.Composable
import com.gonativecoders.whosin.data.auth.model.User

@Composable
fun HomeScreen(
    user: User,
    onLoggedOut: () -> Unit,
    onUserUpdated: (User) -> Unit
) {
    HomeNavigator(
        user = user,
        onUserUpdated = onUserUpdated,
        onLoggedOut = onLoggedOut
    )
}