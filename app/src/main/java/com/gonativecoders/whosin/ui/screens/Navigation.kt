package com.gonativecoders.whosin.ui.screens

import androidx.annotation.StringRes
import com.gonativecoders.whosin.R

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Teams : Screen("teams")
}