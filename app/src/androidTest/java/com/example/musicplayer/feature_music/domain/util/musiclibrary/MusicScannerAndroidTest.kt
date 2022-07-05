package com.example.musicplayer.feature_music.domain.util.musiclibrary

import android.net.Uri
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.google.common.truth.Truth
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class MusicScannerAndroidTest {

    lateinit var musicList: List<MusicTrack>
    lateinit var musicScanner: MusicScanner

    @Before
    fun setUp() {
        musicScanner = MusicScanner()
        musicList = listOf(
            MusicTrack(1L, "", 1, "Papercut", "Hybrid Theory", "LinkinPark", Uri.parse("")),
            MusicTrack(1L, "", 1, "One Step Closer", "Hybrid Theory", "LinkinPark", Uri.EMPTY),
            MusicTrack(1L, "", 1, "Crawling", "Hybrid Theory", "LinkinPark", Uri.EMPTY),
            MusicTrack(1L, "", 1, "Lying From You", "Meteora", "LinkinPark", Uri.EMPTY),
            MusicTrack(1L, "", 1, "From the Inside", "Meteora", "LinkinPark", Uri.EMPTY),
            MusicTrack(1L, "", 1, "Faint", "Meteora", "LinkinPark", Uri.EMPTY),
            MusicTrack(1L, "", 1, "Hold On", "Collateral", "Nervo", Uri.EMPTY),
            MusicTrack(1L, "", 1, "Haute Mess", "Collateral", "Nervo", Uri.EMPTY),
            MusicTrack(1L, "", 1, "Bulletproof", "Collateral", "Nervo", Uri.EMPTY)
        )
    }

    @Test
    fun countNumberOfAlbums(){
        Truth.assertThat(musicScanner.scanForAlbum(musicList).size).isEqualTo(3)
    }

    @Test
    fun countNumberOfArtists(){
        Truth.assertThat(musicScanner.scanForArtist(musicList).size).isEqualTo(2)
    }


}