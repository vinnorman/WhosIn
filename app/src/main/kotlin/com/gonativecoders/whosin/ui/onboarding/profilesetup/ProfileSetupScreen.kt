@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.onboarding.profilesetup

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.auth.model.User
import com.gonativecoders.whosin.core.screens.SelfieCaptureScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ProfileSetupScreen(
    viewModel: ProfileSetupViewModel,
    onProfileSetupComplete: (User) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onBackPressed: () -> Unit,
) {

    var isTakingPhoto by remember { mutableStateOf(false) }
    var newPhotoUri by remember { mutableStateOf<Uri?>(null) }

    if (isTakingPhoto) {
        SelfieCaptureScreen(
            onImageSelected = {
                viewModel.onPhotoUpdated(it)

                isTakingPhoto = false
            },
            onBackPressed = { isTakingPhoto = false })
    } else {
        ProfileSetupContent(
            uiState = viewModel.uiState,
            newPhotoUri = newPhotoUri,
            onNameChanged = viewModel::onNameUpdated,
            onNextClicked = {
                coroutineScope.launch {
                    val updatedUser = viewModel.updateUser(newPhotoUri)
                    onProfileSetupComplete(updatedUser)
                }
            },
            onBackPressed = onBackPressed,
            onTakePhotoClicked = { isTakingPhoto = true }
        )
    }
}

@Composable
fun ProfileSetupContent(
    uiState: ProfileSetupViewModel.UiState,
    newPhotoUri: Uri?,
    onNameChanged: (String) -> Unit,
    onNextClicked: () -> Unit,
    onBackPressed: () -> Unit,
    onTakePhotoClicked: () -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
            imageVector = ImageVector.vectorResource(id = R.drawable.toolbar_background),
            contentDescription = ""
        )
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier,
                    title = {
                        Text(
                            text = "Setup Profile",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = "Account Button",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(bottom = 24.dp, top = 120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .size(150.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {

                            AsyncImage(
                                model = newPhotoUri ?: uiState.imageUri,
                                placeholder = painterResource(id = R.drawable.default_avatar),
                                error = painterResource(id = R.drawable.default_avatar),
                                contentDescription = "Profile photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                        }
                    }
                    Spacer(modifier = Modifier.size(48.dp))
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                        onClick = onTakePhotoClicked
                    ) {
                        Text(text = "Take Photo")
                    }
                    Spacer(modifier = Modifier.size(48.dp))

                    OutlinedTextField(
                        singleLine = true,
                        modifier = Modifier,
                        value = uiState.displayName,
                        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        onValueChange = onNameChanged,
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                        label = { Text("Display Name") },
                        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Full Name") }
                    )
                }

                Column {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = onNextClicked
                    ) {
                        Text(text = "Next")
                    }
                }

            }

        }
    }

}

@Composable
@Preview(showBackground = true)
fun Preview() {
    ProfileSetupContent(
        uiState = ProfileSetupViewModel.UiState(
            displayName = "Vin",
            imageUri = null,
            saving = true
        ),
        newPhotoUri = null,
        onNameChanged = {},
        onBackPressed = {},
        onNextClicked = {},
        onTakePhotoClicked = {}
    )
}