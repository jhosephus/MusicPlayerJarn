package com.example.musicplayer.feature_music.domain.repository

import com.example.musicplayer.core.common.Resource
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    fun getMusic() : Flow<Resource<List<MusicTrack>>>

    fun getAlbums() : Flow<Resource<List<AlbumData>>>

    fun getArtists() : Flow<Resource<List<ArtistData>>>
}