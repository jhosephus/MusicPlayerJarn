package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list

import com.example.musicplayer.feature_music.domain.model.AlbumData

interface AlbumListener {

    fun onAlbumClick(album: AlbumData)
}