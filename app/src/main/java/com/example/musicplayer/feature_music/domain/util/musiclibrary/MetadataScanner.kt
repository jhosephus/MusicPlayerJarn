package com.example.musicplayer.feature_music.domain.util.musiclibrary

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import com.example.musicplayer.feature_music.domain.model.AlbumMetadata
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MetadataScanner {

    companion object {
        fun scanAlbumMetadata(music : List<MusicTrack>) : Flow<List<AlbumMetadata>> {
            return flow {
                var albumList = mutableListOf<AlbumMetadata>()
                val retriever = MediaMetadataRetriever()
                music.forEach { track ->
                    var albumTitle = ""
                    var albumArt : Bitmap? = null
                    var albumAuthor = ""
                    try {
                        retriever.setDataSource(track.data)
                        var albumAuthorMeta =
                            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
                        if (albumAuthorMeta == null) {
                            albumAuthorMeta =
                                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                        }
                        val albumTitleMeta =
                            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
                        albumAuthor = albumAuthorMeta?.trim() ?: "Unknown artist"
                        albumTitle = albumTitleMeta?.trim() ?: "Unknown album"
                    } catch (e : Exception) {
                        e.printStackTrace()
                    }
                    try {
                        var albumArtMeta : ByteArray? = null
                        //retriever.setDataSource(track.data)
                        if (albumTitle != "Unknown album") {
                            albumArtMeta = retriever.embeddedPicture
                            albumArt = if (albumArtMeta != null) {
                                BitmapFactory.decodeByteArray(albumArtMeta, 0, albumArtMeta.size)
                            } else {
                                null
                            }
                        } else {
                            albumArt = null
                        }
                    } catch (e : Exception) {
                        e.printStackTrace()
                    }
                    if (albumList.map { it.title }.contains(albumTitle)) {

                    } else {
                        albumList.add(AlbumMetadata(albumArt, albumTitle, albumAuthor))
                    }
                }
                retriever.release()
                emit(albumList.toList())
            }
        }
    }

}