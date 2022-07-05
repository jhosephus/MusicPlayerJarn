package com.example.musicplayer.core.domain.audio_service

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.musicplayer.core.common.media_player.generic_audio_player.AudioListPlayerGeneric
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@AndroidEntryPoint
class AudioService : LifecycleService() {

    @Inject
    lateinit var audioServiceModel: AudioServiceModel
    private lateinit var audioListPlayerGeneric : AudioListPlayerGeneric

    private val progressTracking = flow<Int> {
        while (
            (audioServiceModel.state.value == AudioServiceState.Paused)
                .or(audioServiceModel.state.value == AudioServiceState.Playing)
                .or(audioServiceModel.state.value == AudioServiceState.Ready)
        ) {
            delay(1000L)
            audioServiceModel.trackState.postValue(audioListPlayerGeneric.currentMediaProgress())
        }
        Log.d("AudioService", "ProgressTracking finishing")
        audioServiceModel.trackState.postValue(0)
    }

    override fun onCreate() {
        super.onCreate()
        audioListPlayerGeneric = AudioListPlayerGeneric(applicationContext) {
            // What to do after a whole playlist is finished
            audioServiceModel.state.postValue(AudioServiceState.Stopped)
        }

        audioServiceModel.command.observe(this, Observer {
            Log.d("AudioService", "Incoming Command is ${it.toString()}")
            when (it) {
                AudioServiceCommand.Next -> {
                    audioListPlayerGeneric.nextMedia()
                    val index = audioServiceModel.playingMediaIndex.value ?: 0
                    if (audioServiceModel.media.value!!.size - 1 > index){
                        audioServiceModel.playingMediaIndex.postValue(index + 1)
                    }
                }
                AudioServiceCommand.None -> {

                }
                AudioServiceCommand.Pause -> {
                    audioListPlayerGeneric.pauseMedia()
                    audioServiceModel.state.postValue(AudioServiceState.Paused)
                }
                AudioServiceCommand.Play -> {
                    audioListPlayerGeneric.resumeMedia()
                    audioServiceModel.state.postValue(AudioServiceState.Playing)
                }
                AudioServiceCommand.Previous -> {
                    audioListPlayerGeneric.previousMedia()
                    val index = audioServiceModel.playingMediaIndex.value ?: 0
                    audioServiceModel.playingMediaIndex.postValue(index - 1)
                }
                is AudioServiceCommand.Progress -> {
                    audioListPlayerGeneric.updateCurrentMediaProgress(it.progress)
                }
                AudioServiceCommand.Stop -> {
                    audioListPlayerGeneric.stopMedia()
                    audioServiceModel.state.postValue(AudioServiceState.Stopped)
                }
                is AudioServiceCommand.UpdateMedia -> {
                    Log.d("AudioService", "Updated")
                    audioListPlayerGeneric.stopMedia()
                    audioListPlayerGeneric.updateMediaList(it.list)
                    audioServiceModel.state.postValue(AudioServiceState.Ready)
                }
                is AudioServiceCommand.StartMedia -> {
                    if (audioServiceModel.state.value != AudioServiceState.Ready) {
                        audioListPlayerGeneric.stopMedia()
                    }
                    Log.d("AudioService", "Starting media")
                    audioListPlayerGeneric.startMediaList(it.startingIndex)
                    audioServiceModel.state.postValue(AudioServiceState.Playing)
                    audioServiceModel.playingMediaIndex.postValue(it.startingIndex)
                    progressTracking.launchIn(lifecycleScope)
                }
            }
        })

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        audioListPlayerGeneric.release()
    }
}