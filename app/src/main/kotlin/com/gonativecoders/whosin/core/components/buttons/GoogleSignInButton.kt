package com.gonativecoders.whosin.core.components.buttons

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    text: String = "Sign in with Google",
    onSignInSuccessful: (SignInCredential) -> Unit,
    onSignInError: (Exception) -> Unit
) {
    val context = LocalContext.current

    val oneTapClient = Identity.getSignInClient(context)

    val signInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        val credential: SignInCredential = oneTapClient.getSignInCredentialFromIntent(result.data)
        onSignInSuccessful(credential)
    }

    val signInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build()
        )
        .setAutoSelectEnabled(true)
        .build()

    val signUpRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .setFilterByAuthorizedAccounts(false)
            .build())
        .build()

    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                try {
                    val request = getIntentRequest(oneTapClient, signInRequest)
                    signInLauncher.launch(request)
                } catch (e: Exception) {
                    try {
                        val request =   getIntentRequest(oneTapClient, signUpRequest)
                        signInLauncher.launch(request)
                    } catch (exception: Exception) {
                        onSignInError(exception)
                    }
                }
            }
        },
        modifier = modifier,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.google),
            modifier = Modifier.size(24.dp),
            contentDescription = ""
        )
        Text(text = text, modifier = Modifier.padding(6.dp))
    }
}


private suspend fun getIntentRequest(
    oneTapClient: SignInClient,
    signUpRequest: BeginSignInRequest
): IntentSenderRequest {
    val result = oneTapClient.beginSignIn(signUpRequest).await()
    return IntentSenderRequest.Builder(result.pendingIntent).build()
}