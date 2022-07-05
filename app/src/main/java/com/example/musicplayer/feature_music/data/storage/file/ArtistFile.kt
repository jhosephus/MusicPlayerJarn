package com.example.musicplayer.feature_music.data.storage.file

import com.example.musicplayer.feature_music.domain.model.ArtistData

data class ArtistFile(
    val id: Long,
    val name: String
) {
    fun toArtistData() : ArtistData {
        return ArtistData(
            id, name
        )
    }
}
