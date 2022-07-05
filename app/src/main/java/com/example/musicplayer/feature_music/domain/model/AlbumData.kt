package com.example.musicplayer.feature_music.domain.model

data class AlbumData(
    val id: Long,
    val albumArt: String,
    val albumTitle: String,
    val artistId : Long
)
