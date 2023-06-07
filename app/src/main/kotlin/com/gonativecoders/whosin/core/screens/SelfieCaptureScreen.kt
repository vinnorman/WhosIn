@file:OptIn(ExperimentalPermissionsApi::class)

package com.gonativecoders.whosin.core.screens

import android.Manifest
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gonativecoders.whosin.core.components.camera.CameraView
import com.gonativecoders.whosin.core.components.camera.CapturedImagePreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun SelfieCaptureScreen(
    onImageSelected: (Uri) -> Unit,
    onBackPressed: () -> Unit
) {

    BackHandler(enabled = true) {
        onBackPressed()
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA) {

    }

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val uri = imageUri
        when {
            uri != null -> CapturedImagePreview(uri = uri, onConfirmClicked = { onImageSelected(uri) }, onRetakeClicked = { imageUri = null })

            permissionState.status.isGranted -> CameraView(onImageCaptured = { imageUri = it }, onError = {})
            else -> {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val textToShow = if (permissionState.status.shouldShowRationale) {
                        "The camera is important for this app. Please grant the permission."
                    } else {

                        "Camera permission required for this feature to be available. " +
                                "Please grant the permission"
                    }
                    Text(text = textToShow, color = Color.White, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.size(24.dp))
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                    }
                }
            }
        }
    }


}