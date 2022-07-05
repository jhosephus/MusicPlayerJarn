package com.example.musicplayer.core.domain.audio_service

sealed class AudioServiceState {
    object Idle : AudioServiceState()
    object Ready : AudioServiceState()
    object Playing : AudioServiceState()
    object Paused : AudioServiceState()
    object Stopped : AudioServiceState()
}
