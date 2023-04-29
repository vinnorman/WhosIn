package com.gonativecoders.whosin.ui.auth

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.rotate
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
import com.gonativecoders.whosin.core.theme.Grey800
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

    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Landscape(
                uiState = uiState,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClicked = onLoginClicked,
                onCreateAccountClicked = onCreateAccountClicked
            )
        }

        else -> {
            Portrait(
                uiState = uiState,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onLoginClicked = onLoginClicked,
                onCreateAccountClicked = onCreateAccountClicked
            )
        }
    }
}



@Composable
private fun Portrait(
    uiState: LoginViewModel.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            LoginScreenLogo(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.login_background_blue),
                contentDescription = null,
                modifier = Modifier.scale(2f),
                contentScale = ContentScale.Crop
            )
            LoginScreenFields(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                uiState, onEmailChange, onPasswordChange, onLoginClicked, onCreateAccountClicked
            )
        }
    }

}

@Composable
private fun Landscape(
    uiState: LoginViewModel.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            LoginScreenLogo(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.weight(1f)) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.login_background_blue),
                contentDescription = null,
                modifier = Modifier
                    .scale(1.85f)
                    .rotate(-90f),
                contentScale = ContentScale.Crop
            )
            LoginScreenFields(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                uiState, onEmailChange, onPasswordChange, onLoginClicked, onCreateAccountClicked
            )
        }
    }
}

@Composable
private fun LoginScreenLogo(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_blue),
            contentDescription = "Welcome Image"
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(text = "Who's In", style = MaterialTheme.typography.headlineMedium, color = Grey800)
    }
}

@Composable
private fun LoginScreenFields(
    modifier: Modifier = Modifier,
    uiState: LoginViewModel.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
        Spacer(modifier = Modifier.size(12.dp))
        TextButton(onClick = onCreateAccountClicked) {
            Text(text = "Create Account", color = Color.White)
        }
        if (uiState.error != null) {
            Text(text = "Whoops! Something went wrong. ${uiState.error}")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Portrait() {
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

@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape"
)
@Composable
private fun Landscape() {
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