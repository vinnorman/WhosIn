package com.gonativecoders.whosin.core.util.photo

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.gonativecoders.whosin.R
import java.io.File

class ComposeFileProvider : FileProvider(R.xml.file_paths) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "profile_photos")
            directory.mkdirs()
            val file = File.createTempFile(
                "profile_photo_",
                ".jpg",
                directory,
            )
            val authority = context.packageName + ".fileprovider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}