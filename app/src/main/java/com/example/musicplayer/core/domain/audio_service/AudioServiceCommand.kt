package com.example.musicplayer.core.domain.audio_service

import com.example.musicplayer.feature_music.domain.model.MusicTrack

sealed class AudioServiceCommand {
    object Stop : AudioServiceCommand()
    object Play : AudioServiceCommand()
    object Pause : AudioServiceCommand()
    object None : AudioServiceCommand()
    data class Progress(val progress : Int) : AudioServiceCommand()
    object Next : AudioServiceCommand()
    object Previous : AudioServiceCommand()
    data class UpdateMedia(val list : List<MusicTrack>) : AudioServiceCommand()
    data class StartMedia(val startingIndex : Int) : AudioServiceCommand()
}
