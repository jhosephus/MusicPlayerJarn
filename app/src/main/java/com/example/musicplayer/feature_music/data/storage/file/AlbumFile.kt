package com.example.musicplayer.feature_music.data.storage.file

import com.example.musicplayer.feature_music.domain.model.AlbumData

data class AlbumFile(
    val id : Long,
    val art : String,
    val title: String,
    val artistId : Long
) {
    fun toAlbumData() : AlbumData {
        return AlbumData(
            id, art, title, artistId
        )
    }
}
