package com.example.musicplayer.core.domain.audio_service

import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.feature_music.domain.model.MusicTrack

class AudioServiceModel {

    val command = MutableLiveData<AudioServiceCommand>(AudioServiceCommand.None)
    val state = MutableLiveData<AudioServiceState>(AudioServiceState.Idle)

    val media = MutableLiveData<List<MusicTrack>>(emptyList())
    val playingMediaIndex = MutableLiveData<Int>(0)
    val trackState = MutableLiveData<Int>(0)

}