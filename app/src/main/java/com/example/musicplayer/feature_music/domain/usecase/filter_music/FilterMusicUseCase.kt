package com.example.musicplayer.feature_music.domain.usecase.filter_music

import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.example.musicplayer.feature_music.domain.util.musiclibrary.MusicScanner
import javax.inject.Inject

class FilterMusicUseCase @Inject constructor(

) {

    operator fun invoke(
        list: List<MusicTrack>,
        filterCriteria: FilterCriteria)
    : List<MusicTrack> {

        return when(filterCriteria) {
            is FilterCriteria.AlbumFilter -> list.filter { it.albumId == filterCriteria.album.id }
            is FilterCriteria.ArtistFilter -> list.filter { it.artistId == filterCriteria.artist.id }
        }
    }

}