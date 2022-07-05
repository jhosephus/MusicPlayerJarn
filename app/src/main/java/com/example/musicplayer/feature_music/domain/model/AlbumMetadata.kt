package com.example.musicplayer.feature_music.domain.model

import android.graphics.Bitmap

data class AlbumMetadata(
    val art : Bitmap?,
    val title : String,
    val artist : String
)
