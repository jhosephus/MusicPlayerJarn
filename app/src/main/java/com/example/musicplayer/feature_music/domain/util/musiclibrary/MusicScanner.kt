package com.example.musicplayer.feature_music.domain.util.musiclibrary

import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.Artist
import com.example.musicplayer.feature_music.domain.model.MusicTrack

class MusicScanner {

    fun scanForAlbum(musicList: List<MusicTrack>) : List<Album> {
        var albumList = mutableListOf<String>()

        musicList.forEach { track ->
            val album = track.album
            if ((album.equals("")) || (album == null)) {
                if ("Sin album" !in albumList) {
                    albumList.add("Sin album")
                }
            } else {
                if (album !in albumList) {
                    albumList.add(album)
                }
            }
        }

        return albumList.map { Album(title = it) }.toList()
    }

    fun scanForArtist(musicList: List<MusicTrack>) : List<Artist> {
        var artistList = mutableListOf<String>()

        musicList.forEach { track ->
            val artist = track.artist
            if ((artist.equals("")) || (artist == null)) {
                if ("Artista desconocido" !in artistList) {
                    artistList.add("Artista desconocido")
                }
            } else {
                if (artist !in artistList) {
                    artistList.add(artist)
                }
            }
        }

        return artistList.map { Artist(name = it) }.toList()
    }

    fun extractAlbum(track: MusicTrack) : Album {
        val album = track.album
        if ((album.equals("")) || (album == null)) {
            return Album("Artista desconocido")
        } else {
            return Album(album)
        }
    }

    fun extractArtist(track: MusicTrack) : Artist {
        val artist = track.artist
        if ((artist.equals("")) || (artist == null)) {
            return Artist("Artista desconocido")
        } else {
            return Artist(artist)
        }
    }

}