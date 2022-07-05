package com.example.musicplayer.core.common.media_player.generic_audio_player

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import com.example.musicplayer.core.common.media_player.GenericMediaPlayer

class AudioPlayerGeneric(
    private val context: Context,
    private val listToSinglePlayerManager: ListToSinglePlayerManager
) : GenericMediaPlayer {

    private var mediaPlayer : MediaPlayer? = MediaPlayer()


    override fun startMedia(uri: Uri) {
        mediaPlayer?.apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(context, uri)
            setOnPreparedListener {
                it.start()
            }
            setOnCompletionListener {
                listToSinglePlayerManager.onSingleAudioCompletion()
            }
            prepareAsync()
        }
    }

    override fun pauseMedia() {
        mediaPlayer!!.pause()
    }

    override fun continueMedia() {
        mediaPlayer!!.start()
    }

    override fun stopMedia() {
        mediaPlayer!!.stop()
        mediaPlayer!!.reset()
    }

    override fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun changeProgress(progress: Int) {
        mediaPlayer?.seekTo(progress)
    }

    override fun currentProgress(): Int {
        return mediaPlayer!!.currentPosition
    }
}