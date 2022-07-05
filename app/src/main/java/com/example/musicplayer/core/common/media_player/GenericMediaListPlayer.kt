package com.example.musicplayer.core.common.media_player

import com.example.musicplayer.feature_music.domain.model.MusicTrack

interface GenericMediaListPlayer {

    fun startMediaList(index : Int)
    fun pauseMedia()
    fun stopMedia()
    fun nextMedia()
    fun previousMedia()
    fun updateMediaList(mediaList: List<MusicTrack>)
    fun release()
    fun resumeMedia()
    fun updateCurrentMediaProgress(progress : Int)
    fun currentMediaProgress() : Int

}