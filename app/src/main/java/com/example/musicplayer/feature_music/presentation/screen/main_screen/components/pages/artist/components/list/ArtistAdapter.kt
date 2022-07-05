package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.artist.components.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.feature_music.domain.model.Artist
import com.example.musicplayer.feature_music.domain.model.ArtistData
import com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list.AlbumViewHolder

class ArtistAdapter(
    private val artistList: List<ArtistData>,
    private val artistListener: ArtistListener
) : RecyclerView.Adapter<ArtistViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArtistViewHolder(
            layoutInflater.inflate(R.layout.item_artist, parent, false),
            artistListener = artistListener)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val item = artistList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }
}