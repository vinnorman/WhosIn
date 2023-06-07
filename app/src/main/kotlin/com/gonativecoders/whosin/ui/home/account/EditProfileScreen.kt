@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.account

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import org.koin.androidx.compose.getViewModel

@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = getViewModel(),
    onCancel: () -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onUserUpdated: (user: User) -> Unit,
) {

    var isTakingPhoto by remember { mutableStateOf(false) }

    if (isTakingPhoto) {
        SelfieCaptureScreen(onImageSelected = {
            viewModel.onImageChange(it)
            isTakingPhoto = false
        })
    } else {
        EditProfileContent(
            uiState = viewModel.uiState,
            onCancel = onCancel,
            onTakePhotoClicked = { isTakingPhoto = true },
            onNameChanged = viewModel::onNameChange,
            onSaveClicked = {
                coroutineScope.launch {
                    val updatedUser = viewModel.saveChanges()
                    onUserUpdated(updatedUser)
                }
            }
        )
    }


}


@Composable
fun EditProfileContent(
    uiState: EditProfileViewModel.UiState,
    onCancel: () -> Unit,
    onTakePhotoClicked: () -> Unit,
    onNameChanged: (String) -> Unit,
    onSaveClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                            text = "Edit Profile",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onCancel) {
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
                    .imePadding(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 120.dp),
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
                                model = uiState.newImageUri ?: uiState.existingImageUri,
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
                        Text(text = if (uiState.existingImageUri == null && uiState.newImageUri == null) "Take Photo" else "Change Photo")
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 48.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        enabled = uiState.changesMade && !uiState.saving,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = onSaveClicked
                    ) {
                        if (uiState.saving) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text(text = "Save")
                        }
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    TextButton(
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        onClick = onCancel
                    ) {
                        Text(text = "Cancel")
                    }


                }

            }

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun EditProfileScreenPreview() {
    EditProfileContent(
        uiState = EditProfileViewModel.UiState("Vin Norman", saving = true),
        onCancel = {},
        onTakePhotoClicked = {},
        onNameChanged = {},
        onSaveClicked = {}
    )
}