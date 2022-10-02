package com.gonativecoders.whosin.ui.screens.home.account

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AccountScreen(onLoggedOut: () -> Unit) {
    Column {
        Text(text = "Your Account")
    }
}