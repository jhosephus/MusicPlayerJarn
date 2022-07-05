package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ItemAlbumBinding
import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.domain.model.MusicTrack
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.File
import java.lang.Exception

class AlbumViewHolder(
    itemView: View,
    private val albumListener: AlbumListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemAlbumBinding.bind(itemView)

    fun render(album: AlbumData) {
        Picasso
            .get()
            .load("file://${album.albumArt}")
            .noPlaceholder()
            .fit()
            .error(R.drawable.album_deafult_art)
            .into(binding.ivAlbumCover, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                    /*val uri = Uri.fromFile(File(album.albumArt))
                    Log.d("AlbumViewHolder", uri.path ?: "")
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(uri.path)
                    val data = retriever.embeddedPicture
                    retriever.release()
                    data.let {
                        if (it != null) {
                            binding.ivAlbumCover.setImageBitmap(
                                BitmapFactory.decodeByteArray(it, 0, it.size)
                            )
                        }
                    }*/
                }

            })


        binding.tvAlbumTitle.text = album.albumTitle
        binding.root.setOnClickListener {
            albumListener.onAlbumClick(album = album)
        }
    }

}