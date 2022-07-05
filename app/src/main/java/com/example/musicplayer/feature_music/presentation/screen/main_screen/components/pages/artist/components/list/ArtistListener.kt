package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist.components.list

import com.example.musicplayer.feature_music.domain.model.ArtistData

interface ArtistListener {
    fun onArtistClick(artist: ArtistData)
}