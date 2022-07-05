package com.example.musicplayer.feature_music.data.storage

import android.graphics.Bitmap
import android.net.Uri

interface VisualMediaProvider {

    fun getBitmapFromPath(path: String) : Bitmap?

}