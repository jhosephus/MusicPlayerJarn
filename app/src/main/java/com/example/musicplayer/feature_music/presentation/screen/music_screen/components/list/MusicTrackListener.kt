package com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list

import com.example.musicplayer.feature_music.domain.model.MusicTrack

interface MusicTrackListener {
    fun OnTrackClick(position : Int, track: MusicTrack)
}