package com.example.musicplayer.core.domain.service

import com.example.musicplayer.feature_music.domain.model.MusicTrack

sealed class PlayerCommand{
    object Stop : PlayerCommand()
    object Play : PlayerCommand()
    object Pause : PlayerCommand()
    object None : PlayerCommand()
    data class Progress(val progress : Int) : PlayerCommand()
}
