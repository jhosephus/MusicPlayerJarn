package com.example.musicplayer.feature_music.domain.usecase.filter_music

import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.domain.model.Artist
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.presentation.viewmodel.MusicEvent

sealed class FilterCriteria{
    data class AlbumFilter(val album: AlbumData) : FilterCriteria()
    data class ArtistFilter(val artist: ArtistData) : FilterCriteria()
}
