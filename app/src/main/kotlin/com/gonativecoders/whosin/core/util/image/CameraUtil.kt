package com.gonativecoders.whosin.core.util.image

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
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

fun ImageCapture.takePhoto(
    context: Context,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val outputOptions = ImageCapture.OutputFileOptions.Builder(context.getTempImageFile()).build()

    takePicture(outputOptions, ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageSavedCallback {
        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val uri = outputFileResults.savedUri ?: return
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