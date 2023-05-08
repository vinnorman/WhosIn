@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.onboarding.profilesetup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gonativecoders.whosin.R
import com.gonativecoders.whosin.core.util.photo.ComposeFileProvider
import com.gonativecoders.whosin.data.auth.model.User

@Composable
fun ProfileSetupScreen(
    viewModel: ProfileSetupViewModel,
    onOnboardingComplete: (User) -> Unit
) {
    ProfileSetupContent(onNextClicked = {
        viewModel.onNextClicked(it) { user -> onOnboardingComplete(user) }
    })
}

@Composable
fun ProfileSetupContent(onNextClicked: (Uri) -> Unit) {
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
                        IconButton(onClick = {

                        }) {
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

                var hasImage by remember {
                    mutableStateOf(false)
                }
                var imageUri by remember {
                    mutableStateOf<Uri?>(null)
                }
                val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                    hasImage = success
                }

                val context = LocalContext.current
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .size(120.dp)
                            .clickable {
                                val uri = ComposeFileProvider.getImageUri(context)
                                imageUri = uri
                                cameraLauncher.launch(uri)
                            },
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
                            if (hasImage) {
                                val image = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUri)
                                    .build()
                                AsyncImage(
                                    model = image,
                                    contentDescription = "Profile photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.padding(12.dp),
                                    imageVector = Icons.Filled.PhotoCamera,
                                    contentDescription = "Take Profile Photo"
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.size(32.dp))
                    Text(text = "Choose your profile photo", style = MaterialTheme.typography.bodyLarge)

                }

                Button(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { onNextClicked(imageUri!!) }) {
                    Text(text = "Next")
                }
            }

        }
    }


}

@Composable
fun ProfileSetupUi() {

}


@Composable
@Preview(showBackground = true)
fun Preview() {
    ProfileSetupContent(onNextClicked = {})
}