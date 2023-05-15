@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.util.photo.ComposeFileProvider
import com.gonativecoders.whosin.data.auth.model.User
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
    EditProfileContent(
        uiState = viewModel.uiState,
        onCancel = onCancel,
        onImageUpdated = viewModel::onImageChange,
        onNameChanged = viewModel::onNameChange,
        onSaveClicked = {
            coroutineScope.launch {
                val updatedUser = viewModel.saveChanges()
                onUserUpdated(updatedUser)
            }
        }
    )
}


@Composable
fun EditProfileContent(
    uiState: EditProfileViewModel.UiState,
    onCancel: () -> Unit,
    onImageUpdated: (String) -> Unit,
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,

                ) {

                var imageUri by remember {
                    mutableStateOf<Uri?>(null)
                }
                val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    onImageUpdated(imageUri?.toString()!!)
                }

                val context = LocalContext.current
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
                                error = painterResource(id = R.drawable.profile),
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
                        onClick = {
                            val uri = ComposeFileProvider.getImageUri(context)
                            imageUri = uri
                            cameraLauncher.launch(uri)
                        }) {
                        Text(text = if (uiState.existingImageUri == null && uiState.newImageUri == null) "Take Photo" else "Change Photo")
                    }
                    Spacer(modifier = Modifier.size(48.dp))
//                    Text(text = "Display Name", style = MaterialTheme.typography.bodySmall)

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

//                    NameField(value = "Some name", onNewValueewValue = {}, placeholder = "Display Name")

                }

                Column(modifier = Modifier.padding(bottom = 48.dp)) {
                    Spacer(modifier = Modifier.size(48.dp))

                    Button(
                        enabled = uiState.changesMade,
                        modifier = Modifier
                            .padding(horizontal = 24.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = onSaveClicked) {
                        Text(text = "Save")
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
        uiState = EditProfileViewModel.UiState("Vin Norman"),
        onCancel = {},
        onImageUpdated = {},
        onNameChanged = {},
        onSaveClicked = {}
    )
}