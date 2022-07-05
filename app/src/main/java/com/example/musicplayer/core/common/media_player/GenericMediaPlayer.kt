package com.example.musicplayer.core.common.media_player

import android.net.Uri

interface GenericMediaPlayer {

    fun startMedia(uri: Uri)

    fun pauseMedia()

    fun continueMedia()

    fun stopMedia()

    fun releasePlayer()

    fun changeProgress(progress : Int)

    fun currentProgress() : Int

}