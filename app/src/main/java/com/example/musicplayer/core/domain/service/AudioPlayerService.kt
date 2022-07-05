package com.example.musicplayer.core.domain.service

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AudioPlayerService : LifecycleService() {

    @Inject
    lateinit var serviceModel: AudioPlayerServiceModel

    private val binder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = MediaPlayer()
    private var currentTrack: MusicTrack? = null
    private var musicPlayerState : MusicPlayerState = MusicPlayerState.Idle
    private var _currentPlayList : List<MusicTrack> = emptyList()
    private var _currentIndex : Int = 0

    private val trackProgress = flow<Int> {
        while (musicPlayerState == MusicPlayerState.PlaylistState(MusicPlayerState.InnerState.Playing)) {
            delay(1000L)
            serviceModel.currentPosition.postValue(mediaPlayer!!.currentPosition)
        }
        serviceModel.currentPosition.postValue(0)
    }

    inner class LocalBinder : Binder() {
        fun getService(): AudioPlayerService = this@AudioPlayerService
    }

    override fun onBind(intent: Intent?): IBinder? {
        super.onBind(intent)
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        serviceModel.playlistInfo.observe(this, Observer {
            _currentPlayList = it.list
            _currentIndex = it.index
            if (_currentPlayList.isNotEmpty()) startPlayList(_currentIndex)
        })

        serviceModel.currentCommand.observe(this, Observer {
            when (it) {
                PlayerCommand.None -> {}
                PlayerCommand.Pause -> pauseCurrentTrack()
                PlayerCommand.Play -> continueCurrentTrack()
                is PlayerCommand.Progress -> changeProgressTrack(it.progress)
                PlayerCommand.Stop -> stopCurrentTrack()
            }
        })


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun pauseCurrentTrack() {
        mediaPlayer!!.pause()
        musicPlayerState = MusicPlayerState.PlaylistState(
            MusicPlayerState.InnerState.Paused
        )
        serviceModel.musicPlayerState.postValue(musicPlayerState)
    }
    private fun continueCurrentTrack() {
        mediaPlayer!!.start()
        musicPlayerState = MusicPlayerState.PlaylistState(
            MusicPlayerState.InnerState.Playing
        )
        serviceModel.musicPlayerState.postValue(musicPlayerState)
    }
    private fun changeProgressTrack(progress : Int) {
        mediaPlayer!!.seekTo(progress)
    }
    private fun stopCurrentTrack() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        musicPlayerState = MusicPlayerState.PlaylistState(
            MusicPlayerState.InnerState.Stopped
        )
        serviceModel.musicPlayerState.postValue(musicPlayerState)
    }

    private fun startPlayList(initialIndex : Int) {
        _currentIndex = initialIndex
        currentTrack = _currentPlayList[_currentIndex]
        if (musicPlayerState != MusicPlayerState.Idle) {
            if (musicPlayerState != MusicPlayerState.Finished) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
            }
        }
        startPlayListSong(currentTrack!!)
    }

    private fun startPlayListSong(musicTrack : MusicTrack) {
        mediaPlayer?.apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(applicationContext, musicTrack.uri)
            setOnPreparedListener {
                it.start()
                serviceModel.currentTrackDuration.postValue(mediaPlayer!!.duration)
                serviceModel.currentTrack.postValue(currentTrack!!)
                musicPlayerState = MusicPlayerState.PlaylistState(
                    MusicPlayerState.InnerState.Playing
                )
                serviceModel.musicPlayerState.postValue(musicPlayerState)
                trackProgress.launchIn(lifecycleScope)
            }
            setOnCompletionListener {
                it.stop()
                it.reset()
                musicPlayerState = MusicPlayerState.PlaylistState(
                    MusicPlayerState.InnerState.Completed
                )
                serviceModel.musicPlayerState.postValue(musicPlayerState)
                if (_currentIndex + 1 < _currentPlayList.size) {
                    _currentIndex++
                    currentTrack = _currentPlayList[_currentIndex]
                    startPlayListSong(currentTrack!!)
                } else {
                    musicPlayerState = MusicPlayerState.PlaylistState(
                        MusicPlayerState.InnerState.Stopped
                    )
                    serviceModel.musicPlayerState.postValue(musicPlayerState)
                }
            }
            prepareAsync()
        }
    }
}