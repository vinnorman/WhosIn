package com.gonativecoders.whosin.ui.home.onboarding

import androidx.compose.runtime.Composable
import com.gonativecoders.whosin.data.auth.model.User

@Composable
fun OnboardingScreen(
    user: User,
    onOnboardingComplete: (User) -> Unit
) {
    OnboardingNavigator(
        user = user,
        onOnboardingComplete = onOnboardingComplete
    )
}