package com.example.musicplayer.feature_music.data.repository

import com.example.musicplayer.core.common.Resource
import com.example.musicplayer.feature_music.data.storage.external.MediaProviderExternal
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.example.musicplayer.feature_music.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MusicRepositoryImpl(
    private val mediaProviderExternal: MediaProviderExternal
) : MusicRepository {
    override fun getMusic(): Flow<Resource<List<MusicTrack>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val musicList = mediaProviderExternal.getMusicFiles()
                emit(Resource.Success(data = musicList.map { it.toMusicTrack() }))
            } catch (e: IOException) {
                emit(Resource.Error(
                    data = emptyList<MusicTrack>(),
                    message = "Unable to scan files"
                ))
            }
        }
    }

    override fun getAlbums(): Flow<Resource<List<AlbumData>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val albumList = mediaProviderExternal.getAlbumFiles()
                emit(Resource.Success(data = albumList.map { it.toAlbumData() }))
            } catch (e: IOException) {
                emit(Resource.Error(
                    data = emptyList<AlbumData>(),
                    message = "Unable to scan files"
                ))
            }
        }
    }

    override fun getArtists(): Flow<Resource<List<ArtistData>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val artistList = mediaProviderExternal.getArtistFiles()
                emit(Resource.Success(data = artistList.map { it.toArtistData() }))
            } catch (e: IOException) {
                emit(Resource.Error(
                    data = emptyList<ArtistData>(),
                    message = "Unable to scan files"
                ))
            }
        }
    }
}