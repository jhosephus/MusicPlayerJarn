package com.example.musicplayer.feature_music.presentation.viewmodel

import com.example.musicplayer.feature_music.domain.model.*
import com.example.musicplayer.feature_music.domain.usecase.filter_music.FilterCriteria

sealed class MusicEvent{
    object ScanMusic : MusicEvent()
    data class SelectTrack(val track: MusicTrack) : MusicEvent()
    data class SelectAlbum(val album: AlbumData) : MusicEvent()
    data class SelectArtist(val artist: ArtistData) : MusicEvent()
    data class FilterMusic(val filterCriteria: FilterCriteria) : MusicEvent()
    object ScanAlbums : MusicEvent()
    object ScanArtist : MusicEvent()
}
