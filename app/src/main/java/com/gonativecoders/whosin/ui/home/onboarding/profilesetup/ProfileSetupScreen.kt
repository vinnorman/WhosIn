@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.gonativecoders.whosin.ui.home.onboarding.profilesetup

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.R

@Composable
fun ProfileSetupScreen(
    onProfileSetupComplete: () -> Unit
) {
    ProfileSetupContent(onProfileSetupComplete)
}

@Composable
fun ProfileSetupContent(onProfileSetupComplete: () -> Unit) {
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

                ProfileSetupUi()
                Button(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = { onProfileSetupComplete() }) {
                    Text(text = "Next")
                }
            }

        }
    }


}

@Composable
fun ProfileSetupUi() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ElevatedCard(
            modifier = Modifier.size(120.dp),
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
                Icon(
                    modifier = Modifier.padding(12.dp),
                    imageVector = Icons.Filled.PhotoCamera,
                    contentDescription = "Take Profile Photo"
                )
            }
        }
        Spacer(modifier = Modifier.size(32.dp))
        Text(text = "Choose your profile photo", style = MaterialTheme.typography.bodyLarge)

    }

}


@Composable
@Preview(showBackground = true)
fun Preview() {
    ProfileSetupContent(onProfileSetupComplete = {})
}