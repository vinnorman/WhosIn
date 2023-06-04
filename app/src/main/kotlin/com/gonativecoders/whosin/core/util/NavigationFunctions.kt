package com.gonativecoders.whosin.core.util

import androidx.navigation.NavHostController

fun NavHostController.navigate(destination: String, clear: Boolean = false, popUpDestination: String? = null) {
    navigate(destination) {
        launchSingleTop = true
        if (clear) popUpTo(0) { inclusive = true }
        else if (popUpDestination != null) popUpTo(popUpDestination) { inclusive = true }
    }
}