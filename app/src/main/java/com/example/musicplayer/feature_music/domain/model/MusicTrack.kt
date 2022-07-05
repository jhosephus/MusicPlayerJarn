package com.example.musicplayer.feature_music.domain.model

import android.net.Uri

data class MusicTrack (
    val id : Long,
    val displayName: String,
    val duration: Int,
    val title: String,
    val album: String,
    val artist: String,
    val albumId: Long,
    val artistId: Long,
    val data: String,
    val uri: Uri
)