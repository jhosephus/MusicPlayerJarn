package com.example.musicplayer.feature_music.presentation.screen.main_screen.components.pages.album.components.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.feature_music.domain.model.Album
import com.example.musicplayer.feature_music.domain.model.AlbumData
import com.example.musicplayer.feature_music.presentation.screen.music_screen.components.list.MusicTrackViewHolder

class AlbumAdapter(
    private val albumList: List<AlbumData>,
    private val albumListener: AlbumListener
) : RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(
            layoutInflater.inflate(R.layout.item_album, parent, false),
            albumListener = albumListener
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = albumList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

}