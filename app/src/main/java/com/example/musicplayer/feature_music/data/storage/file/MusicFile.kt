package com.example.musicplayer.feature_music.data.storage.file

import android.net.Uri
import com.example.musicplayer.feature_music.domain.model.MusicTrack

data class MusicFile(
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
) {
    fun toMusicTrack() : MusicTrack {
        return MusicTrack(
            id, displayName, duration, title, album, artist, albumId, artistId, data, uri
        )
    }
}
