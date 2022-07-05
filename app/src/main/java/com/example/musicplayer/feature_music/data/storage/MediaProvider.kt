package com.example.musicplayer.feature_music.data.storage

import com.example.musicplayer.feature_music.data.storage.file.AlbumFile
import com.example.musicplayer.feature_music.data.storage.file.ArtistFile
import com.example.musicplayer.feature_music.data.storage.file.MusicFile

interface MediaProvider {

    suspend fun getMusicFiles() : List<MusicFile>
    suspend fun getAlbumFiles() : List<AlbumFile>
    suspend fun getArtistFiles() : List<ArtistFile>
}