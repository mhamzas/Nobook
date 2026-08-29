package com.ycngmn.nobook.utils.jsBridge

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.webkit.JavascriptInterface
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.ycngmn.nobook.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class DownloadBridge(private val context: Context) {
    @JavascriptInterface
    fun downloadBase64File(base64Data: String, mimeType: String) {
        runCatching {
            val encodedData = base64Data.substringAfter(',', missingDelimiterValue = "")
            require(encodedData.isNotBlank()) { context.getString(R.string.download_failed_invalid_data) }
            val data = Base64.decode(encodedData, Base64.DEFAULT)
            require(data.isNotEmpty()) { context.getString(R.string.download_failed_invalid_data) }

            val isImage = mimeType.startsWith("image/")
            val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
                ?: if (mimeType.startsWith("video/")) "mp4" else "bin"
            val (finalData, finalMimeType, finalExtension) = if (isImage) {
                val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                if (bitmap != null) {
                    val outputStream = ByteArrayOutputStream()
                    check(bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream))
                    Triple(outputStream.toByteArray(), "image/png", "png")
                } else Triple(data, mimeType, extension)
            } else Triple(data, mimeType, extension)

            val fileName = "${System.currentTimeMillis()}.$finalExtension"
            val saved = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveWithMediaStore(fileName, finalMimeType, finalData)
            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                downloadsDir.mkdirs()
                FileOutputStream(File(downloadsDir, fileName)).use { it.write(finalData) }
                true
            }
            check(saved) { context.getString(R.string.failed_to_save_file) }
            Toast.makeText(context, R.string.saved_to_downloads, Toast.LENGTH_SHORT).show()
        }.onFailure {
            Toast.makeText(context, R.string.failed_to_save_file, Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveWithMediaStore(fileName: String, mimeType: String, data: ByteArray): Boolean {
        val resolver = context.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, mimeType)
            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            put(MediaStore.Downloads.IS_PENDING, 1)
        }
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values) ?: return false
        return runCatching {
            resolver.openOutputStream(uri)?.use { it.write(data) } ?: error("Unable to open output stream")
            values.clear()
            values.put(MediaStore.Downloads.IS_PENDING, 0)
            check(resolver.update(uri, values, null, null) == 1)
            true
        }.getOrElse {
            resolver.delete(uri, null, null)
            false
        }
    }
}
