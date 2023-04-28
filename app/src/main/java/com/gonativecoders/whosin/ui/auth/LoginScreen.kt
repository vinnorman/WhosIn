package com.gonativecoders.whosin.ui.auth

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.components.EmailField
import com.gonativecoders.whosin.core.components.PasswordField
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.data.auth.model.User
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    navigateToRegisterScreen: () -> Unit,
    onLoggedIn: (user: User) -> Unit,
    viewModel: LoginViewModel = getViewModel()
) {

    LoginContent(
        uiState = viewModel.uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChanged,
        onLoginClicked = { viewModel.onLoginClicked(onLoggedIn) },
        onCreateAccountClicked = navigateToRegisterScreen
    )

}

@Composable
fun LoginContent(
    uiState: LoginViewModel.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {

    LoginBackground {
        when (LocalConfiguration.current.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {

                    }
                    Box(modifier = Modifier.weight(1f)) {

                    }
                }
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(80.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                imageVector = ImageVector.vectorResource(id = R.drawable.logo_blue),
                                contentDescription = "Welcome Image"
                            )
                            Text(text = "Who's In")
                        }

                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            EmailField(value = uiState.email, onNewValue = onEmailChange)
                            Spacer(modifier = Modifier.size(16.dp))
                            PasswordField(value = uiState.password, onNewValue = onPasswordChange)
                            Spacer(modifier = Modifier.size(24.dp))
                            OutlinedButton(
                                onClick = onLoginClicked,
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                Text(text = "Log In", modifier = Modifier.padding(12.dp), color = Color.White)
                            }
                            Spacer(modifier = Modifier.size(24.dp))
                            TextButton(onClick = onCreateAccountClicked) {
                                Text(text = "Create Account")
                            }
                            if (uiState.error != null) {
                                Text(text = "Whoops! Something went wrong. ${uiState.error}")
                            }

                        }
                    }
                }
            }
        }

//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(top = 96.dp, start = 24.dp, end = 24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//
//            Spacer(modifier = Modifier.size(12.dp))
//            Text(
//                text = "Who's In",
//                style = MaterialTheme.typography.bodyLarge,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.size(32.dp))

//        }


    }
}

@Composable
fun LoginBackground(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.login_background_blue),
                    contentDescription = null,
                    modifier = Modifier.scale(2f),
                    contentScale = ContentScale.Crop
                )
            }
        }
        content()
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        WhosInTheme {
            LoginContent(
                uiState = LoginViewModel.UiState(),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClicked = {},
                onCreateAccountClicked = {}
            )
        }

    }
}