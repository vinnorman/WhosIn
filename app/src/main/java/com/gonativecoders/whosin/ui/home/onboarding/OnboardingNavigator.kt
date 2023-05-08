package com.gonativecoders.whosin.ui.home.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gonativecoders.whosin.data.auth.model.User
import com.gonativecoders.whosin.ui.home.onboarding.profilesetup.ProfileSetupScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

sealed class OnboardingDestinations(val route: String) {

    object ProfilePhoto : OnboardingDestinations("Profile Photo")
    object PersonalDetails : OnboardingDestinations("Personal Details")
    object JoinTeam : OnboardingDestinations("Join Team")

}


@Composable
fun OnboardingNavigator(
    navController: NavHostController = rememberNavController(),
    user: User,
    onOnboardingComplete: (User) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = OnboardingDestinations.ProfilePhoto.route
    ) {
        composable(route = OnboardingDestinations.ProfilePhoto.route) {
            ProfileSetupScreen(
                viewModel = getViewModel(parameters = { parametersOf(user) }),
                onOnboardingComplete = onOnboardingComplete
            )
        }
    }
}