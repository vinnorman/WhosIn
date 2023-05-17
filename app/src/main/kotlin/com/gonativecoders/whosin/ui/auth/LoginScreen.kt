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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.components.AppLogo
import com.gonativecoders.whosin.core.components.EmailField
import com.gonativecoders.whosin.core.components.ErrorDialog
import com.gonativecoders.whosin.core.components.PasswordField
import com.gonativecoders.whosin.core.theme.WhosInTheme
import com.gonativecoders.whosin.core.auth.model.User
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
        onCreateAccountClicked = navigateToRegisterScreen,
        onErrorDialogDismissed = viewModel::onErrorDialogDismissed
    )
}

@Composable
fun LoginContent(
    uiState: LoginViewModel.UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onCreateAccountClicked: () -> Unit,
    onErrorDialogDismissed: () -> Unit
) {

    if (uiState.error != null) {
        ErrorDialog(
            exception = uiState.error,
            onDismissed = onErrorDialogDismissed
        )
    }

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
        modifier = Modifier.fillMaxSize().imePadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AppLogo(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.weight(1.5f)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.login_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
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
    Row(modifier = Modifier.fillMaxSize().imePadding()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            AppLogo(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.25f)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.login_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .scale(1.25f)
                    .rotate(-90f),
                contentScale = ContentScale.FillBounds
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
            border = BorderStroke(1.dp, Color.White),
            modifier = Modifier.size(height = 48.dp, width = 200.dp)
        ) {
            if (uiState.isLoggingIn) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text(text = "Log In", modifier = Modifier.padding(6.dp), color = Color.White)
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        TextButton(onClick = onCreateAccountClicked) {
            Text(text = "Create Account", color = Color.White)
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
                onCreateAccountClicked = {},
                onErrorDialogDismissed = {}
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
                uiState = LoginViewModel.UiState(isLoggingIn = true),
                onEmailChange = {},
                onPasswordChange = {},
                onLoginClicked = {},
                onCreateAccountClicked = {},
                onErrorDialogDismissed = {}
            )
        }

    }
}