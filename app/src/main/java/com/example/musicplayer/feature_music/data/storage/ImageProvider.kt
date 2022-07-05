package com.example.musicplayer.feature_music.data.storage

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever

class ImageProvider(
    private val context : Context
) : VisualMediaProvider {

    override fun getBitmapFromPath(path: String): Bitmap? {
        val uri = Uri.fromFile(File(path))
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(uri.path)
        val data = retriever.embeddedPicture
        retriever.release()
        return if (data == null) {
            null
        } else {
            BitmapFactory.decodeByteArray(data, 0, data.size)
        }
    }
}