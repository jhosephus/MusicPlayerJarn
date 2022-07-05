package com.example.musicplayer.core.domain.service

import com.example.musicplayer.feature_music.domain.model.MusicTrack

sealed class PlayerState(){
    object Idle : PlayerState()
    object Ready : PlayerState()
    object Stopped : PlayerState()
    data class ChangingTrack(val newTrack: MusicTrack) : PlayerState()
    data class Playing(val playingTrack: MusicTrack) : PlayerState()
    data class Paused(val pausedTrack: MusicTrack) : PlayerState()
}
