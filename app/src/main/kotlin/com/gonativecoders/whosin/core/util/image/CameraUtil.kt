package com.gonativecoders.whosin.core.util.image

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.impl.utils.Exif
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.core.content.ContextCompat
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        val provider = cameraProvider.get()
        cameraProvider.addListener({
            continuation.resume(provider)
        }, ContextCompat.getMainExecutor(this))
    }
}

fun CameraController.takePhoto(
    context: Context,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val file = context.getTempImageFile()
    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).    build()

    takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
        @SuppressLint("RestrictedApi")
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val uri: Uri = outputFileResults.savedUri ?: return

            val exif = Exif.createFromFile(file)
            val rotation = exif.rotation

            onImageCaptured(uri)
        }

        override fun onError(exception: ImageCaptureException) {
            onError(exception)
        }
    })
}

fun Context.getTempImageFile(): File {
    val directory = File(cacheDir, "profile_photos")
    directory.mkdirs()
    return File.createTempFile("profile_photo_", ".jpg", directory)
}