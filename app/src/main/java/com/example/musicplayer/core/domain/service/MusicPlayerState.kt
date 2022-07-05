package com.example.musicplayer.core.domain.service

sealed class MusicPlayerState{
    object Idle : MusicPlayerState()
    data class PlaylistState(val innerState: InnerState) : MusicPlayerState()
    object Finished : MusicPlayerState()

    sealed class InnerState {
        object Playing : InnerState()
        object Paused : InnerState()
        object Stopped : InnerState()
        object Completed : InnerState()
    }

}
