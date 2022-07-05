package com.example.musicplayer.feature_music.data.storage.external

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.example.musicplayer.MusicPlayerApp
import com.example.musicplayer.feature_music.data.storage.MediaProvider
import com.example.musicplayer.feature_music.data.storage.file.AlbumFile
import com.example.musicplayer.feature_music.data.storage.file.ArtistFile
import com.example.musicplayer.feature_music.data.storage.file.MusicFile
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MediaProviderExternal(
    private val context: Context
) : MediaProvider {
    override suspend fun getMusicFiles(): List<MusicFile> {
        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.DATA
            )

            val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

            val tracks = mutableListOf<MusicFile>()
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ARTIST)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                val artistIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val displayName = cursor.getString(displayNameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val title = cursor.getString(titleColumn)
                    val album = cursor.getString(albumColumn) ?: ""
                    val artist = cursor.getString(artistColumn) ?: ""
                    val albumId = cursor.getLong(albumIdColumn)
                    val artistId = cursor.getLong(artistIdColumn)
                    val data = cursor.getString(dataColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    tracks.add(MusicFile(id, displayName, duration, title, album, artist, albumId, artistId, data, contentUri))
                }
                tracks.toList()
            } ?: listOf()
        }
    }

    override suspend fun getAlbumFiles(): List<AlbumFile> {
        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST_ID
            )

            val sortOrder = "${MediaStore.Audio.Albums.ALBUM} ASC"

            val albums = mutableListOf<AlbumFile>()
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID)
                val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM)
                val albumArtColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART)
                val artistIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST_ID)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val album = cursor.getString(albumColumn)
                    val albumArt = cursor.getString(albumArtColumn)
                    val artistId = cursor.getLong(artistIdColumn)
                    albums.add(
                        AlbumFile(id, albumArt ?: "", album ?: "", artistId)
                    )
                }
                albums.toList()
            } ?: listOf()
        }
    }

    override suspend fun getArtistFiles(): List<ArtistFile> {
        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST
            )

            val sortOrder = "${MediaStore.Audio.Artists.ARTIST} ASC"

            val artists = mutableListOf<ArtistFile>()
            context.contentResolver.query(
                collection,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val artist = cursor.getString(artistColumn)
                    artists.add(
                        ArtistFile(id, artist ?: "")
                    )
                }
                artists.toList()
            } ?: listOf()
        }
    }
}