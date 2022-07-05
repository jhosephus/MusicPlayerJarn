package com.example.musicplayer.feature_music.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.core.common.Resource
import com.example.musicplayer.feature_music.domain.model.*
import com.example.musicplayer.feature_music.domain.usecase.filter_music.FilterCriteria
import com.example.musicplayer.feature_music.domain.usecase.filter_music.FilterMusicUseCase
import com.example.musicplayer.feature_music.domain.usecase.scan_album.ScanAlbumUseCase
import com.example.musicplayer.feature_music.domain.usecase.scan_artist.ScanArtistUseCase
import com.example.musicplayer.feature_music.domain.usecase.scan_music.ScanMusicUseCase
import com.example.musicplayer.feature_music.domain.util.musiclibrary.MetadataScanner
import com.example.musicplayer.feature_music.domain.util.musiclibrary.MusicScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val scanMusicUseCase: ScanMusicUseCase,
    private val filterMusicUseCase: FilterMusicUseCase,
    private val scanAlbumUseCase: ScanAlbumUseCase,
    private val scanArtistUseCase: ScanArtistUseCase
) : ViewModel() {

    val music = MutableLiveData<List<MusicTrack>>(emptyList())
    val currentTrack = MutableLiveData<MusicTrack>()
    val currentArtist = MutableLiveData<ArtistData>()
    val currentAlbum = MutableLiveData<AlbumData>()
    val currentSelection = MutableLiveData<List<MusicTrack>>(emptyList())

    val albumResults = MutableLiveData<List<AlbumData>>(emptyList())
    val artistResults = MutableLiveData<List<ArtistData>>(emptyList())


    fun onEvent(musicEvent: MusicEvent) {
        when (musicEvent) {
            is MusicEvent.ScanMusic -> readMusic()
            is MusicEvent.SelectTrack -> selectTrack(musicEvent.track)
            is MusicEvent.SelectAlbum -> selectAlbum(musicEvent.album)
            is MusicEvent.SelectArtist -> selectArtist(musicEvent.artist)
            is MusicEvent.FilterMusic -> filterMusic(musicEvent.filterCriteria)
            is MusicEvent.ScanAlbums -> readAlbums()
            is MusicEvent.ScanArtist -> readArtists()
        }
    }

    private fun readMusic() {
        scanMusicUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Error -> music.postValue(emptyList())
                is Resource.Loading -> {}
                is Resource.Success -> {
                    music.postValue(result.data!!)
                    //Log.d("AlbumScan", MetadataScanner.scanAlbumMetadata(result.data!!).toString())
                    /*MetadataScanner.scanAlbumMetadata(result.data!!).onEach {
                        Log.d("AlbumScan", it.toString())
                    }.launchIn(viewModelScope)*/
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun selectTrack(musicTrack: MusicTrack){
        currentTrack.postValue(musicTrack)
    }

    private fun selectArtist(artist: ArtistData){
        currentArtist.postValue(artist)
    }

    private fun selectAlbum(album: AlbumData){
        currentAlbum.postValue(album)
    }

    private fun filterMusic(filterCriteria: FilterCriteria){
        currentSelection.postValue(filterMusicUseCase.invoke(music.value!!, filterCriteria))
    }

    private fun readAlbums() {
        scanAlbumUseCase.invoke().onEach { result ->
            when(result) {
                is Resource.Error -> albumResults.postValue(emptyList())
                is Resource.Loading -> {}
                is Resource.Success -> {
                    albumResults.postValue(result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun readArtists() {
        scanArtistUseCase.invoke().onEach { result ->
            when(result) {
                is Resource.Error -> artistResults.postValue(emptyList())
                is Resource.Loading -> {}
                is Resource.Success -> {
                    artistResults.postValue(result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }


}