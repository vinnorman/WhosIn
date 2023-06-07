package com.gonativecoders.whosin.core.util.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream

const val compressionQuality = 25

fun Context.compress(uri: String): ByteArray {
    return compress(Uri.parse(uri))
}

fun Context.compress(uri: Uri): ByteArray {
    return contentResolver.openInputStream(uri).use { data ->
        val bitmap = BitmapFactory.decodeStream(data)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressionQuality, outputStream)
        outputStream.toByteArray()
    }
}