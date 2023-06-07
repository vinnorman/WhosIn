package com.gonativecoders.whosin.core.components.camera

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gonativecoders.whosin.R

@Composable
fun CapturedImagePreview(
    uri: Uri,
    onConfirmClicked: () -> Unit,
    onRetakeClicked: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = uri,
            contentDescription = "Profile photo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            CapturedImageControls(onControlClicked = {
                when (it) {
                    ImagePreviewAction.OnConfirm -> onConfirmClicked()
                    ImagePreviewAction.OnRetake -> onRetakeClicked()
                }
            })
        }
    }
}


@Composable
fun CapturedImageControls(
    onControlClicked: (ImagePreviewAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        CameraControl(
            Icons.Sharp.Close,
            R.string.icn_image_preview_cancel_content_description,
            modifier = Modifier.size(48.dp),
            onClick = { onControlClicked(ImagePreviewAction.OnRetake) }
        )

        CameraControl(
            Icons.Sharp.CheckCircle,
            R.string.icn_camera_image_preview_confirm_content_description,
            modifier = Modifier
                .size(48.dp),
            onClick = { onControlClicked(ImagePreviewAction.OnConfirm) }
        )
    }
}


enum class ImagePreviewAction {
    OnConfirm, OnRetake
}




