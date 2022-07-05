package com.example.musicplayer.core.common.media_player.generic_audio_player

import android.content.Context
import com.example.musicplayer.core.common.media_player.GenericMediaListPlayer
import com.example.musicplayer.feature_music.domain.model.MusicTrack

class AudioListPlayerGeneric(
    private val context: Context,
    private val onMediaFinished : () -> Unit
) : GenericMediaListPlayer{
    private var audioPlayerGeneric = AudioPlayerGeneric(
        context = context,
        object : ListToSinglePlayerManager {
            override fun onSingleAudioCompletion() {
                if (musicList.size - 1 == currentMediaIndex) {
                    stopMedia()
                    onMediaFinished()
                } else {
                    nextMedia()
                }
            }
        }
    )
    private var musicList : List<MusicTrack> = emptyList()
    private var currentMediaIndex : Int = 0

    override fun startMediaList(index: Int) {
        currentMediaIndex = index
        audioPlayerGeneric.startMedia(musicList[index].uri)
    }

    override fun pauseMedia() {
        audioPlayerGeneric.pauseMedia()
    }

    override fun stopMedia() {
        audioPlayerGeneric.stopMedia()
    }

    override fun nextMedia() {
        if (musicList.size - currentMediaIndex > 1) {
            currentMediaIndex++
            audioPlayerGeneric.stopMedia()
            audioPlayerGeneric.startMedia(musicList[currentMediaIndex].uri)
        }
    }

    override fun previousMedia() {
        if (currentMediaIndex > 0) {
            currentMediaIndex--
            audioPlayerGeneric.stopMedia()
            audioPlayerGeneric.startMedia(musicList[currentMediaIndex].uri)
        }
    }

    override fun updateMediaList(mediaList: List<MusicTrack>) {
        musicList = mediaList
    }

    override fun release() {
        audioPlayerGeneric.releasePlayer()
    }

    override fun resumeMedia() {
        audioPlayerGeneric.continueMedia()
    }

    override fun updateCurrentMediaProgress(progress: Int) {
        audioPlayerGeneric.changeProgress(progress)
    }

    override fun currentMediaProgress(): Int {
        return audioPlayerGeneric.currentProgress()
    }
}