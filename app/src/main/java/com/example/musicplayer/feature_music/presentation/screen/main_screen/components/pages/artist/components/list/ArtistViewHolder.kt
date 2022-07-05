package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist.components.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ItemAlbumBinding
import com.example.musicplayer.databinding.ItemArtistBinding
import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.Artist
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list.AlbumListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ArtistViewHolder(
    itemView: View,
    private val artistListener: ArtistListener
) : RecyclerView.ViewHolder(itemView)  {

    private val binding = ItemArtistBinding.bind(itemView)

    fun render(artist: ArtistData) {
        Picasso
            .get()
            .load("file://${artist.name}")
            .noPlaceholder()
            .fit()
            .error(R.drawable.album_deafult_art)
            .into(binding.ivArtistPic, object : Callback {
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {
                    e?.printStackTrace()
                }

            })
        binding.tvArtistname.text = artist.name
        binding.root.setOnClickListener {
            artistListener.onArtistClick(artist = artist)
        }
    }

}