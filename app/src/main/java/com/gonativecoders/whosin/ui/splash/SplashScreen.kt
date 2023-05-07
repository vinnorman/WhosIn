package com.gonativecoders.whosin.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    onLoggedOut: () -> Unit,
    onLoggedIn: (user: User) -> Unit,
    onOnboarding: (user: User) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = getViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
    }
    LaunchedEffect(true) {
        delay(1000)
        when (val loginStatus = viewModel.getLoginStatus()) {
            is SplashViewModel.LoginStatus.LoggedIn -> onLoggedIn(loginStatus.user)
            SplashViewModel.LoginStatus.LoggedOut -> onLoggedOut()
            is SplashViewModel.LoginStatus.Onboarding -> onOnboarding(loginStatus.user)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            SplashScreen(onLoggedOut = {}, onLoggedIn = {}, onOnboarding = {})
        }
    }
}
