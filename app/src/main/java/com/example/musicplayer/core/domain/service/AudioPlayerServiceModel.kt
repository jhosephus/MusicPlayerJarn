package com.example.musicplayer.core.domain.service

import androidx.lifecycle.MutableLiveData
import com.example.musicplayer.feature_music.domain.model.MusicTrack

class AudioPlayerServiceModel constructor(
) {
    val currentCommand = MutableLiveData<PlayerCommand>(PlayerCommand.None)

    val currentPosition = MutableLiveData<Int>(0)
    val currentTrackDuration = MutableLiveData<Int>(1000)
    val currentTrack = MutableLiveData<MusicTrack?>(null)

    val playlistInfo = MutableLiveData<PlaylistInfo>(
        PlaylistInfo(emptyList(), 0)
    )
    data class PlaylistInfo(
        val list : List<MusicTrack>,
        val index : Int
    )

    val musicPlayerState = MutableLiveData<MusicPlayerState>(MusicPlayerState.Idle)


}